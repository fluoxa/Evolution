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
    private final Strategy constMutationMixedParentSelection;
    @Getter
    private final Strategy ageBasedMutationRandomParentSelection;

    @Autowired
    public IocConfig(EvoConfig evoConfig) {
        this.evoConfig = evoConfig;

        constMutationMixedParentSelection = new Strategy(ParentSelection.mixedParentSelectionFactory(evoConfig.getDeterministicRandomParentRation()),
                NaturalSelection.RANK_RANDOM_SELECTION,
                Mutation.getConstantRateMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        ageBasedMutationRandomParentSelection = new Strategy(ParentSelection.mixedParentSelectionFactory(evoConfig.getDeterministicRandomParentRation()),
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