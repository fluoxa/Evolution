package de.baleipzig.configuration;

import de.baleipzig.configuration.strategies.*;
import de.baleipzig.genome.DoubleGenome;
import de.baleipzig.genome.Genome;
import de.baleipzig.genome.IntermediateRecombinationGenome;
import de.baleipzig.individual.Individual;
import de.baleipzig.individual.SuperHero;
import de.baleipzig.population.Avengers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IocConfig {

    private final EvoConfig evoConfig;

    private final Strategy strategy = new Strategy(ParentSelection.RANDOM_SELECTION,
            NaturalSelection.RANDOM_SELECTION,
            Mutation.CHERNOBYL_MUTATION,
            GenomeRecombination.BRUTAL_CROSSING);

    @Bean
    @Scope("prototype")
    public Avengers getPopulation() {
        return new Avengers(evoConfig.getPopulationConfig(), strategy);
    }

    @Bean
    @Scope("prototype")
    public Individual getIndividual() {

        Function<DoubleGenome, Double> fitnessFunction = genome -> genome.getAllele(0);
        return new SuperHero(fitnessFunction, strategy);
    }

    @Bean
    @Scope("prototype")
    public Genome getGenome() {
        return new IntermediateRecombinationGenome(evoConfig.getGenomeConfig());
    }
}