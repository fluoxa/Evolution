package de.baleipzig.configuration.strategies;

import de.baleipzig.genome.Genome;

public class GenomeRecombination {

    @FunctionalInterface
    public interface GenomeRecombinationFunction {

        void recombine(Genome motherGenome, Genome fatherGenome, Genome childGenome);
    }

    public static final GenomeRecombinationFunction BRUTAL_CROSSING = (motherGenome , fatherGenome, childGenome) -> {

    };
}
