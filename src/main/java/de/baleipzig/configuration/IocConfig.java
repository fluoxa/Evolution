package de.baleipzig.configuration;

import de.baleipzig.encoding.Genome;
import de.baleipzig.encoding.IntermediateRecombGenome;
import de.baleipzig.individual.Individual;
import de.baleipzig.individual.SuperHero;
import de.baleipzig.population.Avengers;
import de.baleipzig.population.Population;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IocConfig {

    private final EvoConfig evoConfig;

    @Bean
    @Scope("prototype")
    public Population getPopulation() {
        return new Avengers(evoConfig.getPopulationConfig());
    }

    @Bean
    @Scope("prototype")
    public Individual getIndividual() {
        return new SuperHero();
    }

    @Bean
    @Scope("prototype")
    public Genome getGenome() {
        return new IntermediateRecombGenome(evoConfig.getGenomeConfig());
    }
}