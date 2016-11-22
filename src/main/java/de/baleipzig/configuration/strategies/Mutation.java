package de.baleipzig.configuration.strategies;

import de.baleipzig.configuration.GenomeConfig;
import de.baleipzig.genome.Genome;
import de.baleipzig.individual.Individual;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Component
public class Mutation {

    public static final Function<Individual<Genome<Double>>, Individual<Genome<Double>>> CHERNOBYL_MUTATION = individual -> {

        List<Double> alleles = individual.getGenome().getAlleles();
        List<Double> shuffledGenome = new ArrayList<>(alleles.size());

        Collections.shuffle(alleles);
        shuffledGenome.addAll(alleles);
        individual.getGenome().setAlleles(shuffledGenome);

        return individual;
    };

    public static Function<Individual<Genome<Double>>, Individual<Genome<Double>>> getConstantRateMutationStrategy(GenomeConfig config)  {
        return  individual -> {

            if(individual.getAge() > 0) {
                return individual;
            }

            individual.getGenome().setAlleles(mutateDoubleGenome(config, individual, config.getMutationRate()));

            return individual;
        };
    }

    public static Function<Individual<Genome<Double>>, Individual<Genome<Double>>> getAgeBasedIncreasingMutationStrategy(GenomeConfig config)  {
        return individual -> {
            //(1+log(1+age)/3)
            individual.getGenome().setAlleles(mutateDoubleGenome(config, individual, config.getMutationRate() * (1+Math.log(1+individual.getAge()))/3));
            return individual;
        };
    }

    public static Function<Individual<Genome<Double>>, Individual<Genome<Double>>> getAgeBasedDecreasingMutationStrategy(GenomeConfig config)  {
        return individual -> {
            //mutationrate/(1+age)
            individual.getGenome().setAlleles(mutateDoubleGenome(config, individual, config.getMutationRate() / (1+individual.getAge())));
            return individual;
        };
    }

    private static List<Double> mutateDoubleGenome(GenomeConfig config, Individual<Genome<Double>> individual, double mutationRate) {

        List<Double> mutatedGenes = new ArrayList<>(individual.getGenome().getGenomeLength());

        individual.getGenome().getAlleles().forEach(allele -> {
            if(Math.random() < mutationRate) {
                double alleleShift = 2*(Math.random() - 0.5)*config.getMutationMaxLength();
                allele += alleleShift;

                allele = Math.min(allele, config.getUpperBound());
                allele = Math.max(allele, config.getLowerBound());
            }

            mutatedGenes.add(allele);
        });

        return mutatedGenes;
    }
}