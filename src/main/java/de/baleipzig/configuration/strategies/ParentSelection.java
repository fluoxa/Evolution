package de.baleipzig.configuration.strategies;


import de.baleipzig.individual.Individual;
import de.baleipzig.individual.Parents;
import de.baleipzig.population.Population;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ParentSelection {

    public static final Function<Population, Parents> FITTEST_PARENT_SELECTION = population -> {
        Parents parents = new Parents();

        List<Individual> potentialParents = population.getIndividuals();

        Collections.sort(potentialParents, Collections.reverseOrder());

        parents.setFather(potentialParents.get(0));
        parents.setMother(potentialParents.get(1));
        return parents;
    };
}
