package de.baleipzig.configuration.strategies;

import de.baleipzig.genome.Genome;

public class GenomeRecombination {

    @FunctionalInterface
    public interface GenomeRecombinationFunction {

        void recombine(Genome motherGenome, Genome fatherGenome, Genome childGenome);
    }

    public static final GenomeRecombinationFunction INTERMEDIATE_RECOMBINATION = (motherGenome , fatherGenome, childGenome) -> {

        for(int pos = 0; pos < childGenome.getGenomeLength(); pos++){
            double averageAllele = ((double) motherGenome.getAllele(pos)+ (double) fatherGenome.getAllele(pos))/2;
            childGenome.setAllele(pos, averageAllele);
        }
    };
}
