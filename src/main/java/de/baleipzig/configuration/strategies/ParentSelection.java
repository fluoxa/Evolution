package de.baleipzig.configuration.strategies;


import de.baleipzig.individual.Parents;
import de.baleipzig.population.Population;

import java.util.function.Function;

public class ParentSelection {

    public static final Function<Population, Parents> RANDOM_SELECTION = population -> {
        Parents parents = new Parents();
        parents.setFather(population.getIndividuals().get(0));
        parents.setMother(population.getIndividuals().get(1));
        return parents;
    };
}
