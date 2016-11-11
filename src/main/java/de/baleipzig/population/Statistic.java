package de.baleipzig.population;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Statistic {

    @Getter
    private double mean;
    @Getter
    private double standardDeviation;
    @Getter
    private double bestFitness;
}
