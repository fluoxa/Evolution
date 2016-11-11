package de.baleipzig.population;

import de.baleipzig.individual.Individual;

import java.util.List;

public interface Population {

    void populate();

    void add(Individual individual);

    List<Individual> getIndividuals();

    void createNextGeneration();

    Individual getFittestIndividual();
    Statistic createStatistic();
}
