package de.baleipzig.individual;

import de.baleipzig.genome.Genome;

import java.util.function.Function;

public class SuperHero<T extends Genome> extends AbstractIndividual<T> {

    public SuperHero(Function<T, Double> fitnessFunction){
        super(fitnessFunction);
    }


    @Override
    public Individual<T> mateWith(Individual<T> individual) {
        return null;
    }

    @Override
    public Individual<T> mutate() {
        return null;
    }
}
