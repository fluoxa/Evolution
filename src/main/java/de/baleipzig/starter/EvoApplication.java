package de.baleipzig.starter;

import de.baleipzig.configuration.EvoConfig;
import de.baleipzig.individual.Individual;
import de.baleipzig.population.Population;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvoApplication {

    private final EvoConfig evoConfig;
    private final ApplicationContext context;
    private Population avengers;

    public void start() {
        avengers = context.getBean(Population.class);
        avengers.populate();

        List<Individual> initIndividuals = avengers.getIndividuals();

        Collections.sort(initIndividuals);

        System.out.println("Initial Population:");
        for (Individual individual : initIndividuals) {
            System.out.println(individual.getFitness());
        }

        for (int cycle = 0; cycle < evoConfig.getEvolutionCycles(); cycle++) {
           avengers.createNextGeneration();
        }

        List<Individual> individuals = avengers.getIndividuals();

        Collections.sort(individuals);

        System.out.println("Final Population:");

        for (Individual individual : individuals) {
            System.out.println(individual.getFitness());
        }

        System.out.println("Fittest member:");

        System.out.println(avengers.getFittestIndividual().getFitness());
    }
}
