package de.baleipzig.population;

import de.baleipzig.genome.Genome;
import de.baleipzig.individual.Individual;

import java.util.List;

public interface IPopulation<Individual<? extends Genome>> {

    void populate();

    void add(T individual);

    List<T> getPopulation();

    Population<T> createNextGeneration();

    Individual getFittestIndividual();
}
