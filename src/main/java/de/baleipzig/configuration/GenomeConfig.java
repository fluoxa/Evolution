package de.baleipzig.configuration;

import lombok.Data;

@Data
public class GenomeConfig {
    private int numberOfGenes;
    private double lowerBound;
    private double upperBound;
    private double mutationRate;
    private double mutationMaxLength;
}
