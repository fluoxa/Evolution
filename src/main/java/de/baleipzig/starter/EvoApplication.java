package de.baleipzig.starter;

import de.baleipzig.configuration.EvoConfig;
import de.baleipzig.population.Population;
import de.baleipzig.population.Statistic;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvoApplication {

    private final EvoConfig evoConfig;
    private final ApplicationContext context;
    private Population avengers;

    public void start() {
        avengers = context.getBean(Population.class);
        avengers.populate();

        for (int cycle = 0; cycle < evoConfig.getEvolutionCycles(); cycle++) {
            avengers.createNextGeneration();


            if(cycle % 10 == 0) {
                Statistic statistic = avengers.createStatistic();
                System.out.println(String.format("%s Fitness Mean: %.4f  +/-  %.4f  Best Fitness: %.4f", cycle, statistic.getMean(), statistic.getStandardDeviation(), statistic.getBestFitness()));
            }
        }

        System.out.println("Final Statistic:");
        Statistic statistic = avengers.createStatistic();
        System.out.println(String.format("Fitness Mean: %.4f  +/-  %.4f  Best Fitness: %.4f", statistic.getMean(), statistic.getStandardDeviation(), statistic.getBestFitness()));
    }
}
