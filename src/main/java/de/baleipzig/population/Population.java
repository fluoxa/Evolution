package de.baleipzig.population;

import de.baleipzig.configuration.PopulationConfig;
import de.baleipzig.genome.Genome;
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
import java.util.function.Function;

@ToString
@RequiredArgsConstructor
public class Population<Individual<? extends Genome>> implements IPopulation<Individual> {

    //region -- member --

    @Autowired
    private ApplicationContext context;

    private final PopulationConfig config;
    private final Function<Population, Parents> parentSelection;
    private final Function<Population, Population> naturalSelection;

    @Getter
    protected List<Individual> population = new ArrayList<>(config.getPopulationSize());

    //endregion

    //region -- methods --

    @Override
    public void add(Individual individual) {
         population.add(individual);
    }

    @Override
    public Individual getFittestIndividual() {

        Collections.sort(population);

        return population.get(0);
    }

    @Override
    public Population createNextGeneration() {

        Population intermediateGeneration = context.getBean(Population.class);

        for(int i = 0; i < config.getIntermediateGenerationSize(); i++) {

            Parents parents = selectParents(this);
            intermediateGeneration.add(parents.getMother().mateWith(parents.getFather()));
        }

        return selectNaturally(intermediateGeneration);
    }

    @Override
    public void populate() {

        population = new ArrayList<>(config.getPopulationSize());

        for (int pos = 0; pos < config.getPopulationSize(); pos++) {

            population.add((Individual)context.getBean(Individual.class));
        }
    }

    private Population selectNaturally(Population population) {

        return naturalSelection.apply(population);
    }

    private Parents selectParents(Population population) {

        return parentSelection.apply(population);
    }

    //endregion
}
