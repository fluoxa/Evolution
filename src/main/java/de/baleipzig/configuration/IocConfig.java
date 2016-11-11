package de.baleipzig.configuration;

import de.baleipzig.configuration.strategies.*;
import de.baleipzig.genome.DoubleGenome;
import de.baleipzig.genome.Genome;
import de.baleipzig.individual.Individual;
import de.baleipzig.individual.SuperHero;
import de.baleipzig.population.Avengers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class IocConfig {

    private final EvoConfig evoConfig;

    private Strategy selectedStrategy;

    private final Strategy constMutationRankSelection;

    @Autowired
    public IocConfig(EvoConfig evoConfig) {
        this.evoConfig = evoConfig;

        constMutationRankSelection = new Strategy(ParentSelection.RANDOM_PARENT_SELECTION,
                NaturalSelection.RANK_RANDOM_SELECTION,
                Mutation.getConstantRateMutationStrategy(evoConfig.getGenomeConfig()),
                GenomeRecombination.INTERMEDIATE_RECOMBINATION);
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