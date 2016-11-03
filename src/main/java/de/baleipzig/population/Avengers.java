package de.baleipzig.population;

import de.baleipzig.configuration.PopulationConfig;
import de.baleipzig.individual.Individual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;

public class Avengers extends AbstractPopulation<Individual> {

    @Autowired
    private ApplicationContext context;

    public Avengers(PopulationConfig config) {
        super(config);
    }

    @Override
    protected Population breed() {
        return null;
    }

    @Override
    protected Population naturalSelection() {
        // Umwelt-Selektion: wähle rangbasierte Selektion
        // (fitteste am wahrscheinlichsten,
        //  schlechte unwahrscheinlich)
         return null;
    }

    @Override
    public void populate() {

        population = new ArrayList<>(config.getPopulationSize());

        for (int pos = 0; pos < config.getPopulationSize(); pos++) {

            population.add(context.getBean(Individual.class));
        }
    }

    @Override
    public Population createNextGeneration() {

        return null;
    }

    @Override
    public Individual getFittestIndividual() {
        return null;
    }
}
