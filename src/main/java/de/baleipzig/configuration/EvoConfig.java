package de.baleipzig.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(locations="classpath:Application.yml", prefix="de.baleipzig.evoconfig", merge = true)
public class EvoConfig {

    private int maxGenerations;
    private double deterministicRandomParentRatio;
    private double rankRandomRatioNaturalSelection;
    private GenomeConfig genomeConfig;
    private PopulationConfig populationConfig;
    private TasksConfig tasksConfig;
    private int logGriewankDimension;
    private double logNthRankRandomRatioStep;
}