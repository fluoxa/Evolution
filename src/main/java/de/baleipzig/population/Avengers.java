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

        Collections.sort(individuals);

        return individuals.get(0);
    }

    @Override
    public void createNextGeneration() {

        List<Individual> children = new ArrayList<>(config.getIntermediateGenerationSize());

        for(int i = 0; i < config.getIntermediateGenerationSize(); i++) {

            Parents parents = selectParents(this);

            Individual child = parents.getMother().mateWith(parents.getFather());

            child.mutate();

            children.add(child);
        }

        individuals.addAll(children);

        //merge population with childPopulation

        selectNaturally(individuals);
    }

    @Override
    public void populate() {

        individuals = new ArrayList<>(config.getPopulationSize());

        for (int pos = 0; pos < config.getPopulationSize(); pos++) {

            individuals.add(context.getBean(Individual.class));
        }
    }

    private void selectNaturally(List<Individual> individuals) {

        strategy.getNaturalSelection().accept(individuals);
    }

    private Parents selectParents(Population avengers) {

        return strategy.getParentSelection().apply(avengers);
    }

    //endregion
}