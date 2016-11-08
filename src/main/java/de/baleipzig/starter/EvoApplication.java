package de.baleipzig.starter;

import de.baleipzig.configuration.EvoConfig;
import de.baleipzig.population.Population;
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
        }

        System.out.println("Fittest member:");
        System.out.println(avengers.getFittestIndividual().getFitness());
    }
}
