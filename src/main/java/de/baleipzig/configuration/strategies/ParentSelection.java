package de.baleipzig.configuration.strategies;


import de.baleipzig.individual.Individual;
import de.baleipzig.individual.Parents;
import de.baleipzig.population.Population;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ParentSelection {

    public static final Function<Population, Parents> RANDOM_PARENT_SELECTION =
            population -> selectListOperation(population, individuals -> {
                Collections.shuffle(individuals);
                return individuals;
        });


    public static final Function<Population, Parents> DETERMINISTIC_PARENT_SELECTION =
            population -> selectListOperation(population, individuals -> {
                Collections.sort(individuals);
                Long consideredIndividualCount = Math.round(individuals.size() * 0.2);
                individuals = individuals.subList(0, consideredIndividualCount.intValue());
                Collections.shuffle(individuals);
                return individuals;
            });

    public static final Function<Population, Parents> MIXED_PARENT_SELECTION =
            population -> {
                double randomValue = Math.random();
                if(randomValue < 0.5) {
                    return DETERMINISTIC_PARENT_SELECTION.apply(population);
                } else {
                    return RANDOM_PARENT_SELECTION.apply(population);
                }
            };


    private static Parents selectListOperation(Population population, Function<List<Individual>, List<Individual>> function) {
        Parents parents = new Parents();

        List<Individual> potentialParents = function.apply(population.getIndividuals());

        Collections.sort(potentialParents, Collections.reverseOrder());

        parents.setFather(potentialParents.get(0));
        parents.setMother(potentialParents.get(1));
        return parents;
    }


    public static final Function<Population, Parents> FITTEST_PARENT_SELECTION = population -> {
        Parents parents = new Parents();

        List<Individual> potentialParents = population.getIndividuals();

        Collections.sort(potentialParents, Collections.reverseOrder());

        parents.setFather(potentialParents.get(0));
        parents.setMother(potentialParents.get(1));
        return parents;
    };
}
