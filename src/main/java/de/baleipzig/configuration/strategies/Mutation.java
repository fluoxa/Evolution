package de.baleipzig.configuration.strategies;

import de.baleipzig.configuration.GenomeConfig;
import de.baleipzig.genome.Genome;
import de.baleipzig.individual.Individual;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Consumer;

@Component
public class Mutation {

    public static final Consumer<Individual> CHERNOBYL_MUTATION = individual -> {
        Collections.shuffle(individual.getGenome().getAlleles());
    };

    public static Consumer<Individual<Genome<Double>>> getConstantRateMutationStrategy(GenomeConfig config)  {
        return  individual -> {

            if(individual.getAge() > 0) {
                return;
            }

            mutateDoubleGenome(config, individual, config.getMutationRate());
        };
    }

    public static Consumer<Individual<Genome<Double>>> getAgeBasedRateMutationStrategy(GenomeConfig config)  {
        return  individual -> {
            mutateDoubleGenome(config, individual, config.getMutationRate() * Math.sqrt(individual.getAge()));
        };
    }

    private static void mutateDoubleGenome(GenomeConfig config, Individual<Genome<Double>> individual, double mutationRate) {
        individual.getGenome().getAlleles().forEach(allele -> {
            if(Math.random() < mutationRate) {
                double rnd = Math.random();
                double alleleShift = 2*(Math.random() - 0.5)*config.getMutationMaxLength();
                allele += alleleShift;

                allele = Math.min(allele, config.getUpperBound());
                allele = Math.max(allele, config.getLowerBound());
            }
        });
    }
}
