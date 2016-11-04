package de.baleipzig.configuration.strategies;


import de.baleipzig.individual.Parents;
import de.baleipzig.population.Population;

import java.util.function.Function;

public class ParentSelection {

    public static final Function<Population, Parents> RANDOM_SELECTION = population -> {
        return new Parents();
    };
}
