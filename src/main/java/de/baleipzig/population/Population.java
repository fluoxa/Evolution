package de.baleipzig.population;

import de.baleipzig.encoding.Genome;
import de.baleipzig.individual.Individual;

public interface Population<T extends Individual<? extends Genome>> {

    void populate();

    Population createNextGeneration();

    Individual getFittestIndividual();
}
