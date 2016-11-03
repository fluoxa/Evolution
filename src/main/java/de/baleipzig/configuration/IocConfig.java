package de.baleipzig.configuration;

import de.baleipzig.encoding.Genome;
import de.baleipzig.encoding.RealGenome;
import de.baleipzig.individual.Individual;
import de.baleipzig.individual.SuperHeros;
import de.baleipzig.population.Avengers;
import de.baleipzig.population.Population;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class IocConfig {

    private final EvoConfig evoConfig;

    @Bean
    public Population getPopulation() {
        return new Avengers();
    }

    @Bean
    public Individual getIndividual() {
        return new SuperHeros();
    }

    @Bean
    public Genome getGenome() {
        return new RealGenome(evoConfig.getNumberOfGenes());
    }
}
