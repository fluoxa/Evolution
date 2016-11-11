package de.baleipzig.population;

import de.baleipzig.configuration.PopulationConfig;
import de.baleipzig.configuration.strategies.Strategy;
import de.baleipzig.individual.Individual;
import de.baleipzig.individual.Parents;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
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

        List<Individual> children = new ArrayList<>(config.getChildrenGenerationSize());

        for(int i = 0; i < config.getChildrenGenerationSize(); i++) {

            Parents parents = selectParents(this);

            Individual child = parents.getMother().mateWith(parents.getFather());

            children.add(child);
        }

        individuals.addAll(children);

        // testen, ob genome tatsächlich mutiert wird
        individuals.forEach(Individual::mutate);

        individuals = selectNaturally(individuals);

        individuals.forEach(Individual::incrementAge);
    }

    @Override
    public void populate() {

        individuals = new ArrayList<>(config.getPopulationSize());

        for (int pos = 0; pos < config.getPopulationSize(); pos++) {

            individuals.add(context.getBean(Individual.class));
        }
    }

    @Override
    public Statistic createStatistic() {

        DescriptiveStatistics statistics = new DescriptiveStatistics();

        individuals.forEach(individual -> statistics.addValue(individual.getFitness()));

        return new Statistic(statistics.getMean(), statistics.getStandardDeviation(), statistics.getMax());
    }

    private List<Individual> selectNaturally(List<Individual> individuals) {

        return strategy.getNaturalSelection().apply(individuals, config.getPopulationSize());
    }

    private Parents selectParents(Population avengers) {

        return strategy.getParentSelection().apply(avengers);
    }

    //endregion
}