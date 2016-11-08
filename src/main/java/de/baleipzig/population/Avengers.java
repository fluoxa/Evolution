package de.baleipzig.population;

import de.baleipzig.configuration.PopulationConfig;
import de.baleipzig.configuration.strategies.Strategy;
import de.baleipzig.individual.Individual;
import de.baleipzig.individual.Parents;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
@RequiredArgsConstructor
public class Avengers implements Population {

    //region -- member --

    @Autowired
    private ApplicationContext context;

    private final PopulationConfig config;
    private final Strategy strategy;

    @Getter
    protected List<Individual> individuals = new ArrayList<>();

    //endregion

    //region -- methods --

    @Override
    public void add(Individual individual) {

        individuals.add(individual);
    }

    @Override
    public Individual getFittestIndividual() {

        Collections.sort(individuals, Collections.reverseOrder());

        return individuals.get(0);
    }

    @Override
    public void createNextGeneration() {

        List<Individual> children = new ArrayList<>(config.getIntermediateGenerationSize());

        for(int i = 0; i < config.getIntermediateGenerationSize(); i++) {

            Parents parents = selectParents(this);

            Individual child = parents.getMother().mateWith(parents.getFather());

            // testen, ob genome tatsächlich mutiert wird
            child.mutate();

            children.add(child);
        }

        individuals.addAll(children);

        // war notwendig, hier auf rückgabewert umzustellen,
        // da sonst scheinbar auf einer flachen kopie der liste operiert wird
        individuals = selectNaturally(individuals);
    }

    @Override
    public void populate() {

        individuals = new ArrayList<>(config.getPopulationSize());

        for (int pos = 0; pos < config.getPopulationSize(); pos++) {

            individuals.add(context.getBean(Individual.class));
        }
    }

    private List<Individual> selectNaturally(List<Individual> individuals) {

        return strategy.getNaturalSelection().apply(individuals, config.getPopulationSize());
    }

    private Parents selectParents(Population avengers) {

        return strategy.getParentSelection().apply(avengers);
    }

    //endregion
}