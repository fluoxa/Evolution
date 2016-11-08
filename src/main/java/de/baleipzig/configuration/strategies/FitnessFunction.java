package de.baleipzig.configuration.strategies;

import de.baleipzig.genome.Genome;

import java.util.function.Function;

public class FitnessFunction {


    public static Function<Genome<Double>, Double> griewankFitnessFactory(int numberOfArgs) {

        Function<Genome<Double>, Double> griewankFunc = (genome) -> {

            double sum = 0.;
            double prod = 1.;
            double allele = 0.;

            for (int i = 0; i < numberOfArgs; i++) {

                allele = genome.getAllele(i);
                sum += allele * allele;
                prod *= Math.cos(allele/Math.sqrt((double) i+1));
            }

            // given: the larger the value the fitter the genome -> algo will find maximum!
            // hence, the negative of the griewank function has to be considered in order to find its minimum
            return -1. - sum/(400. * numberOfArgs) + prod;
        };

        return griewankFunc;
    }

    public final static Function<Genome<Double>, Double> MINIMIZE_ERROR = genome ->  genome.getAllele(0);
}
