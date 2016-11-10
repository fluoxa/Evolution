package de.baleipzig.configuration.strategies;

import de.baleipzig.individual.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class NaturalSelection {

    public static final BiFunction<List<Individual>, Integer, List<Individual>> RANDOM_SELECTION = (individuals, populationSize) -> {

        Collections.shuffle(individuals);
        individuals = individuals.subList(0, populationSize);
        return individuals;
    };

    public static final BiFunction<List<Individual>, Integer, List<Individual>> RANK_RANDOM_SELECTION = (candidates, populationSize) -> {

        List<Individual> selectedIndividuals = new ArrayList<>(populationSize);

        Collections.sort(candidates, Collections.reverseOrder());

        Supplier<Individual> rankStrategy = () -> candidates.get(getRankBasedIndex(candidates.size()));
        Supplier<Individual> shuffleStrategy = () -> candidates.get((int) (candidates.size()*Math.random()));

        fillListUpTo(selectedIndividuals, populationSize/2, rankStrategy);
        fillListUpTo(selectedIndividuals, populationSize, shuffleStrategy);

        return selectedIndividuals;
    };

    private static int getRankBasedIndex(int populationSize) {

        double rnd = Math.random();
        int j = 0;
        double accumulatedProbability = rankProbability(j, populationSize);

        while( accumulatedProbability <= rnd ) {
            j++;
            accumulatedProbability += rankProbability(j, populationSize);
        }

        return j;
    }

    private static double rankProbability(int pos, int length) {

        return 2./length*(1. - ((double) pos)/(length - 1.));
    }

    private static void fillListUpTo(List<Individual> list, int aimedSize,  Supplier<Individual> supplier) {

        while(list.size() < aimedSize) {

            Individual candidate = supplier.get();

            if(!list.contains(candidate)) {
                list.add(candidate);
            }
        }
    }
}
