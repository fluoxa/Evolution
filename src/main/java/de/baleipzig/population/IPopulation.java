package de.baleipzig.population;

import de.baleipzig.individual.Individual;

import java.util.List;

public interface IPopulation {

    void populate();

    void add(Individual individual);

    List<Individual> getIndividuals();

    IPopulation createNextGeneration();

    Individual getFittestIndividual();
}
