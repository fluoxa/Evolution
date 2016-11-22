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
    private final Strategy ageBasedIncreasingMutationMixedParentSelection;
    @Getter
    private final Strategy ageBasedDecreasingMutationMixedParentSelection;
    @Getter
    private final Strategy caroTimStrategy;

    @Autowired
    public IocConfig(EvoConfig evoConfig) {
        this.evoConfig = evoConfig;

        constMutationMixedParentSelection = new Strategy(ParentSelection.mixedParentSelectionFactory(evoConfig.getDeterministicRandomParentRatio()),
                NaturalSelection.rankRandomSelectionFactory(evoConfig.getRankRandomRatioNaturalSelection()),
                Mutation.getConstantRateMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        ageBasedIncreasingMutationMixedParentSelection = new Strategy(ParentSelection.mixedParentSelectionFactory(evoConfig.getDeterministicRandomParentRatio()),
                NaturalSelection.rankRandomSelectionFactory(evoConfig.getRankRandomRatioNaturalSelection()),
                Mutation.getAgeBasedIncreasingMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        ageBasedDecreasingMutationMixedParentSelection = new Strategy(ParentSelection.mixedParentSelectionFactory(evoConfig.getDeterministicRandomParentRatio()),
                NaturalSelection.rankRandomSelectionFactory(evoConfig.getRankRandomRatioNaturalSelection()),
                Mutation.getAgeBasedDecreasingMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        caroTimStrategy = new Strategy(
                ParentSelection.mixedParentSelectionFactory(0.),
                NaturalSelection.rankRandomSelectionFactory(evoConfig.getRankRandomRatioNaturalSelection()),
                Mutation.getAgeBasedDecreasingMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);

        switch (evoConfig.getTasksConfig().getStrategy()) {
            case "constMutationMixedParentSelection":
                selectedStrategy = constMutationMixedParentSelection;
                break;
            case "ageBasedIncreasingMutationMixedParentSelection":
                selectedStrategy = ageBasedIncreasingMutationMixedParentSelection;
                break;
            case "ageBasedDecreasingMutationMixedParentSelection":
                selectedStrategy = ageBasedDecreasingMutationMixedParentSelection;
                break;
            default:
                throw new RuntimeException(evoConfig.getTasksConfig().getStrategy() + "not found");
        }
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