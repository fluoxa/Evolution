package de.baleipzig.results;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.baleipzig.configuration.EvoConfig;
import de.baleipzig.configuration.TasksConfig;
import de.baleipzig.configuration.strategies.Strategy;
import de.baleipzig.population.Population;
import de.baleipzig.population.Statistic;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileDataGenerator {

    private final EvoConfig evoConfig;
    private final ApplicationContext context;
    private final DescriptiveStatistics resultStatistic = new DescriptiveStatistics();
    private final DescriptiveStatistics cycleStatistic = new DescriptiveStatistics();

    private List<List<Pair<Double, Double>>> resultList = new ArrayList<>();
    private List<Pair<Integer, Double>> generationBestFitnessList = new ArrayList<>();
    private List<List<Double>> resultErrorList = new ArrayList<>();
    private List<List<Double>> optimumList = new ArrayList<>();

    private boolean generationFitnessListFilled = false;

    private Map<Integer, Integer> dimensionListPositionMapper = new HashMap<>();

    @Autowired
    public FileDataGenerator (EvoConfig evoConfig, ApplicationContext context) {

        this.evoConfig = evoConfig;
        this.context = context;
    }

    public void generateData(String strategy) {

        init(strategy);
        calcData();
        saveData();
    }

    private String createOutputPath() {
        String path = evoConfig.getTasksConfig().getOutputPath()
                + evoConfig.getTasksConfig().getStrategy()
                + "ratio" + evoConfig.getRankRandomRatioNaturalSelection()
                + "mut" + evoConfig.getGenomeConfig().getMutationRate()
                + "pop" + evoConfig.getPopulationConfig().getPopulationSize()
                + "child" + evoConfig.getPopulationConfig().getChildrenGenerationSize();

        return path;
    }

    private void init(String strategy) {

        File directory = new File(createOutputPath());

        if(directory.exists()) {
            directory.delete();
        }

        directory.mkdirs();

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(createOutputPath()+"/evoConfig.json"), evoConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TasksConfig tasksConfig = evoConfig.getTasksConfig();

        if(strategy != null && !strategy.isEmpty()) {
            tasksConfig.setStrategy(strategy);
        }

        resultList = new ArrayList<>(tasksConfig.getConsideredGriewankDimensions().size());
        generationBestFitnessList = new ArrayList<>(evoConfig.getMaxGenerations());
        resultErrorList = new ArrayList<>(tasksConfig.getConsideredGriewankDimensions().size());
        optimumList = new ArrayList<>(tasksConfig.getConsideredGriewankDimensions().size());

        int pos = 0;

        for (int dim : tasksConfig.getConsideredGriewankDimensions() ) {
            resultList.add(new ArrayList<>(tasksConfig.getNumberOfSamplingPoints()));
            resultErrorList.add(new ArrayList<>(tasksConfig.getNumberOfSamplingPoints()));
            optimumList.add(new ArrayList<>(tasksConfig.getNumberOfSamplingPoints()));
            dimensionListPositionMapper.put(dim, pos++);
        }
    }

    private void calcData() {

        dimensionListPositionMapper.entrySet().forEach(this::calcDataForGriewankDimension);
    }

    private void calcDataForGriewankDimension(Map.Entry<Integer, Integer> dimPosPair) {
        System.out.printf("Dimension %s\n", dimPosPair.getKey());
        evoConfig.getGenomeConfig().setNumberOfGenes(dimPosPair.getKey());

        int samplingPoints = evoConfig.getTasksConfig().getNumberOfSamplingPoints();

        for (int count = 0; count < samplingPoints+1; count++) {
            System.out.printf("ParentRatio %1.2f\n", 0 + ((double) count) / ((double) samplingPoints));

            evoConfig.setDeterministicRandomParentRatio(0 + ((double) count) / ((double) samplingPoints));
            Strategy.setParentSelectionRatio(evoConfig.getDeterministicRandomParentRatio());

            int listNumber = dimPosPair.getValue();
            calcDataForSingleEvolutionCycle(listNumber, dimPosPair.getKey(), count);
        }

        System.out.println("Calculation done for number of genes: " + dimPosPair.getKey());
    }

    private void calcDataForSingleEvolutionCycle(int listNumber, int griewankDim, int nthRankRandomStep) {

        resultStatistic.clear();
        cycleStatistic.clear();

        for(int run = 0; run < evoConfig.getTasksConfig().getRunsPerDataPoint(); run++) {

            Population avengers = context.getBean(Population.class);
            avengers.populate();

            int cycle;

            System.out.printf("Durchlauf %s Generation:", run);

            for (cycle = 0; cycle < evoConfig.getMaxGenerations(); cycle++) {
                avengers.createNextGeneration();

                Statistic statistic = avengers.createStatistic();

                if(!generationFitnessListFilled && griewankDim == evoConfig.getLogGriewankDimension() && nthRankRandomStep == evoConfig.getLogNthRankRandomStep()) {
                    generationBestFitnessList.add(new Pair<>(cycle, statistic.getBestFitness()));
                }

                if (statistic.getStandardDeviation() < 0.0001 || Math.abs(statistic.getBestFitness()) < 0.0001) {
                    break;
                }

                if(cycle % 250 == 0) {
                    System.out.printf(" %s", cycle);
                }
            }

            System.out.println("");

            if(griewankDim == evoConfig.getLogGriewankDimension() && nthRankRandomStep == evoConfig.getLogNthRankRandomStep()) {
                generationFitnessListFilled = true;
            }

            resultStatistic.addValue(avengers.getFittestIndividual().getFitness());
        }

        resultList.get(listNumber).add(new Pair<>(evoConfig.getDeterministicRandomParentRatio(), resultStatistic.getMean()));
        resultErrorList.get(listNumber).add(resultStatistic.getStandardDeviation());
        optimumList.get(listNumber).add(resultStatistic.getMax());
    }

    private void saveData() {

        StringBuilder sb = new StringBuilder();
        int maxLineNumber = resultList.get(0).size();
        int numberOfConsideredCases = resultList.size();

        String header = "";

        for(int crtCase = 0; crtCase < numberOfConsideredCases; crtCase++) {

            header = String.format("%sratio; average_%s; err_%s; optimum_%s; ",
                    header,
                    evoConfig.getTasksConfig().getConsideredGriewankDimensions().get(crtCase),
                    evoConfig.getTasksConfig().getConsideredGriewankDimensions().get(crtCase),
                    evoConfig.getTasksConfig().getConsideredGriewankDimensions().get(crtCase)
            );
        }

        sb.append(header);
        sb.append('\n');

        for(int lineNumber = 0; lineNumber < maxLineNumber; lineNumber++) {

            String line = "";

            for(int crtCase = 0; crtCase < numberOfConsideredCases; crtCase++) {

                line = String.format("%s%.3f; %.8f; %.6f; %.8f; ",
                        line,
                        resultList.get(crtCase).get(lineNumber).getKey(),
                        -resultList.get(crtCase).get(lineNumber).getValue(),
                        resultErrorList.get(crtCase).get(lineNumber),
                        -optimumList.get(crtCase).get(lineNumber)
                        );
            }

            sb.append(line);
            sb.append('\n');
        }

        try {
            PrintWriter writer = new PrintWriter(createOutputPath()+"/results.dat", "UTF-8");
            writer.printf(sb.toString());
            writer.close();
        }
        catch( IOException ex){
            System.out.println(ex);
        }

        StringBuilder sbGenFit = new StringBuilder();

        for(Pair<Integer, Double> data : generationBestFitnessList) {

            sbGenFit.append(String.format("%d; %.8f; \n", data.getKey(), -data.getValue()));
        }

        try {
            PrintWriter generationFitnessWriter = new PrintWriter(createOutputPath()+"/generatioFitness_griewank"+evoConfig.getLogGriewankDimension()+"_"+ evoConfig.getLogNthRankRandomStep()+"thStep.dat", "UTF-8");
            generationFitnessWriter.printf(sbGenFit.toString());
            generationFitnessWriter.close();
        }
        catch( IOException ex){
            System.out.println(ex);
        }
    }
}