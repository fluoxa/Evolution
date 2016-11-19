package de.baleipzig.starter;

import de.baleipzig.configuration.EvoConfig;
import de.baleipzig.population.Population;
import de.baleipzig.population.Statistic;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvoApplication {

    private final EvoConfig evoConfig;
    private final ApplicationContext context;
    private final DescriptiveStatistics resultStatistic = new DescriptiveStatistics();
    private final DescriptiveStatistics cycleStatistic = new DescriptiveStatistics();


    public void start() {

        for(int run = 0; run < 25; run++) {

            Population avengers = context.getBean(Population.class);
            avengers.populate();

            int cycle;

            for (cycle = 0; cycle < evoConfig.getEvolutionCycles(); cycle++) {
                avengers.createNextGeneration();

                Statistic statistic = avengers.createStatistic();

//                if (cycle % 10 == 0) {
//                    System.out.println(String.format("%s Fitness Mean: %.4f  +/-  %.4f  Best Fitness: %.4f", cycle, statistic.getMean(), statistic.getStandardDeviation(), statistic.getBestFitness()));
                    //                avengers.getIndividuals().stream().sorted().forEach(individual -> System.out.printf("%.4f   ", individual.getFitness()));
                    //                System.out.println("");
//                }

                if (statistic.getStandardDeviation() < 0.0001 || Math.abs(statistic.getBestFitness()) < 0.0001) {
                    break;
                }
            }

            resultStatistic.addValue(avengers.getFittestIndividual().getFitness());
            cycleStatistic.addValue(cycle);

//            System.out.println("Final Statistic:");
//            Statistic statistic = avengers.createStatistic();
//            System.out.println(String.format("%s Fitness Mean: %.4f  +/-  %.4f  Best Fitness: %.4f", cycle, statistic.getMean(), statistic.getStandardDeviation(), statistic.getBestFitness()));
        }

        System.out.printf("Number of Results: %s \n", resultStatistic.getN());
        System.out.println("Result-Analysis:");
        System.out.printf("Mean: %.4f  Standard deviation: %.4f \n", resultStatistic.getMean(), resultStatistic.getStandardDeviation());
        System.out.println("Cycle-Analysis:");
        System.out.printf("Mean: %.4f  Standard deviation: %.4f \n", cycleStatistic.getMean(), cycleStatistic.getStandardDeviation());
    }
}