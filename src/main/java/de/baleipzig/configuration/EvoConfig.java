package de.baleipzig.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(locations="classpath:Application.yml", prefix="de.baleipzig.evoconfig")
public class EvoConfig {

    private int evolutionCycles;
    private GenomeConfig genomeConfig;
    private PopulationConfig populationConfig;
}