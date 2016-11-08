package de.baleipzig.configuration.strategies;

import de.baleipzig.individual.Individual;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class NaturalSelection {
    public static final BiFunction<List<Individual>, Integer, List<Individual>> RANDOM_SELECTION = (individuals, populationSize) -> {

        Collections.shuffle(individuals);
        individuals = individuals.subList(0, populationSize);
        return individuals;
    };
}
