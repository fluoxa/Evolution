package de.baleipzig.configuration;

import de.baleipzig.configuration.strategies.*;
import de.baleipzig.genome.DoubleGenome;
import de.baleipzig.genome.Genome;
import de.baleipzig.individual.Individual;
import de.baleipzig.individual.SuperHero;
import de.baleipzig.population.Avengers;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class IocConfig {

    private final EvoConfig evoConfig;

    @Setter
    private Strategy selectedStrategy;

    @Getter
    private final Strategy constMutationRandomParentSelection;
    @Getter
    private final Strategy constMutationDeterministicParentSelection;
    @Getter
    private final Strategy constMutationMixedParentSelection;
    @Getter
    private final Strategy ageBasedMutationRandomParentSelection;
    @Getter
    private final Strategy ageBasedMutationDeterministicParentSelection;
    @Getter
    private final Strategy ageBasedMutationMixedParentSelection;

    @Autowired
    public IocConfig(EvoConfig evoConfig) {
        this.evoConfig = evoConfig;

        constMutationRandomParentSelection = new Strategy(ParentSelection.RANDOM_PARENT_SELECTION,
                NaturalSelection.RANK_RANDOM_SELECTION,
                Mutation.getConstantRateMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        constMutationDeterministicParentSelection = new Strategy(ParentSelection.DETERMINISTIC_PARENT_SELECTION,
                NaturalSelection.RANK_RANDOM_SELECTION,
                Mutation.getConstantRateMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        constMutationMixedParentSelection = new Strategy(ParentSelection.MIXED_PARENT_SELECTION,
                NaturalSelection.RANK_RANDOM_SELECTION,
                Mutation.getConstantRateMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);


        ageBasedMutationRandomParentSelection = new Strategy(ParentSelection.RANDOM_PARENT_SELECTION,
                NaturalSelection.RANK_RANDOM_SELECTION,
                Mutation.getAgeBasedRateMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        ageBasedMutationDeterministicParentSelection = new Strategy(ParentSelection.DETERMINISTIC_PARENT_SELECTION,
                NaturalSelection.RANK_RANDOM_SELECTION,
                Mutation.getAgeBasedRateMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        ageBasedMutationMixedParentSelection = new Strategy(ParentSelection.MIXED_PARENT_SELECTION,
                NaturalSelection.RANK_RANDOM_SELECTION,
                Mutation.getAgeBasedRateMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        selectedStrategy = constMutationMixedParentSelection;
    }

    @Bean
    @Scope("prototype")
    public Avengers getPopulation() {
        return new Avengers(evoConfig.getPopulationConfig(), selectedStrategy);
    }

    @Bean
    @Scope("prototype")
    public Individual getIndividual() {

        return new SuperHero(FitnessFunction.griewankFitnessFactory(evoConfig.getGenomeConfig().getNumberOfGenes()), selectedStrategy);
    }

    @Bean
    @Scope("prototype")
    public Genome getGenome() {
        return new DoubleGenome(evoConfig.getGenomeConfig());
    }
}