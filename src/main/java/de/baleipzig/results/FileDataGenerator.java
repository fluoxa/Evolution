package de.baleipzig.results;

import de.baleipzig.configuration.EvoConfig;
import de.baleipzig.configuration.TasksConfig;
import de.baleipzig.population.Population;
import de.baleipzig.population.Statistic;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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
    private List<List<Double>> resultErrorList = new ArrayList<>();
    private List<List<Pair<Double, Double>>> cycleList = new ArrayList<>();
    private List<List<Double>> cycleErrorList = new ArrayList<>();

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

    private void init(String strategy) {

        TasksConfig tasksConfig = evoConfig.getTasksConfig();
        tasksConfig.setStrategy(strategy);

        resultList = new ArrayList<>(tasksConfig.getConsideredGriewankDimensions().size());
        resultErrorList = new ArrayList<>(tasksConfig.getConsideredGriewankDimensions().size());
        cycleList = new ArrayList<>(tasksConfig.getConsideredGriewankDimensions().size());
        cycleErrorList = new ArrayList<>(tasksConfig.getConsideredGriewankDimensions().size());

        int pos = 0;

        for (int dim : tasksConfig.getConsideredGriewankDimensions() ) {
            resultList.add(new ArrayList<>(tasksConfig.getNumberOfSamplingPoints()));
            resultErrorList.add(new ArrayList<>(tasksConfig.getNumberOfSamplingPoints()));
            cycleErrorList.add(new ArrayList<>(tasksConfig.getNumberOfSamplingPoints()));
            cycleList.add(new ArrayList<>(tasksConfig.getNumberOfSamplingPoints()));
            dimensionListPositionMapper.put(dim, pos++);
        }
    }

    private void calcData() {

        dimensionListPositionMapper.entrySet().forEach(this::calcDataForGriewankDimension);
    }

    private void calcDataForGriewankDimension(Map.Entry<Integer, Integer> dimPosPair) {

        evoConfig.getGenomeConfig().setNumberOfGenes(dimPosPair.getKey());

        int samplingPoints = evoConfig.getTasksConfig().getNumberOfSamplingPoints();

        for (int count = 0; count < samplingPoints+1; count++) {

            evoConfig.setDeterministicRandomParentRatio(0 + ((double) count) / ((double) samplingPoints));

            int listNumber = dimPosPair.getValue();
            calcDataForSingleEvolutionCycle(listNumber);
        }
    }

    private void calcDataForSingleEvolutionCycle(int listNumber) {

        resultStatistic.clear();
        cycleStatistic.clear();

        for(int run = 0; run < evoConfig.getTasksConfig().getRunsPerDataPoint(); run++) {

            Population avengers = context.getBean(Population.class);
            avengers.populate();

            int cycle;

            for (cycle = 0; cycle < evoConfig.getEvolutionCycles(); cycle++) {
                avengers.createNextGeneration();

                Statistic statistic = avengers.createStatistic();

                if (statistic.getStandardDeviation() < 0.0001 || Math.abs(statistic.getBestFitness()) < 0.0001) {
                    break;
                }
            }

            resultStatistic.addValue(avengers.getFittestIndividual().getFitness());
            cycleStatistic.addValue(cycle);
        }

        resultList.get(listNumber).add(new Pair<>(evoConfig.getDeterministicRandomParentRatio(), resultStatistic.getMean()));
        resultErrorList.get(listNumber).add(resultStatistic.getStandardDeviation());
        cycleList.get(listNumber).add(new Pair<>(evoConfig.getDeterministicRandomParentRatio(),cycleStatistic.getMean()));
        cycleErrorList.get(listNumber).add(cycleStatistic.getStandardDeviation());
    }

    private void saveData() {

        StringBuilder sb = new StringBuilder();
        int maxLineNumber = resultList.get(0).size();
        int numberOfConsideredCases = resultList.size();

        for(int lineNumber = 0; lineNumber < maxLineNumber; lineNumber++) {

            String line = "";

            for(int crtCase = 0; crtCase < numberOfConsideredCases; crtCase++) {

                line = String.format("%s%.3f; %.3f; %.3f; %.3f; %.3f; %.3f; ",
                        line,
                        resultList.get(crtCase).get(lineNumber).getKey(),
                        resultList.get(crtCase).get(lineNumber).getValue(),
                        resultErrorList.get(crtCase).get(lineNumber),
                        cycleList.get(crtCase).get(lineNumber).getKey(),
                        cycleList.get(crtCase).get(lineNumber).getValue(),
                        cycleErrorList.get(crtCase).get(lineNumber)
                        );
            }

            sb.append(line);
            sb.append('\n');
        }

        try {
            PrintWriter writer = new PrintWriter(evoConfig.getTasksConfig().getOutputPath()+"/results.dat", "UTF-8");
            writer.printf(sb.toString());
            writer.close();
        }
        catch( IOException ex){}
    }
}