package de.baleipzig.configuration.strategies;

import de.baleipzig.individual.Individual;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class NaturalSelection {
    public static final Consumer<List<Individual>> RANDOM_SELECTION = individuals -> {
        int newSize = individuals.size()/2;
        Collections.shuffle(individuals);

        individuals = individuals.subList(0, newSize);
    };
}
