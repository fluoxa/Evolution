package de.baleipzig.individual;

import de.baleipzig.genome.Genome;

public interface Individual<T extends Genome> extends Comparable<Individual<T>> {

    Double getFitness();

    Individual<T> mateWith(Individual<T> individual);

    Individual<T> mutate();
}
