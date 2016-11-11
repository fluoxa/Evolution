package de.baleipzig.individual;

import de.baleipzig.genome.Genome;

public interface Individual<T extends Genome> extends Comparable<Individual<T>> {

    Double getFitness();
    T getGenome();

    int getAge();
    void incrementAge();

    Individual<T> mateWith(Individual<T> individual);

    void mutate();
}
