package de.baleipzig.configuration.strategies;

import de.baleipzig.individual.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class NaturalSelection {

    public static final BiFunction<List<Individual>, Integer, List<Individual>> rankRandomSelectionFactory(double rankRandomRation) {

        return (candidates, populationSize) -> {

            List<Individual> selectedIndividuals = new ArrayList<>(populationSize);
            List<Individual> remainingCandidates = new ArrayList<>(candidates);

            Collections.sort(remainingCandidates, Collections.reverseOrder());

            Supplier<Individual> rankStrategy = () -> {
                Individual candidate = remainingCandidates.get(getRankBasedIndex(remainingCandidates.size()));
                remainingCandidates.remove(candidate);
                return candidate;
            };

            Supplier<Individual> shuffleStrategy = () -> {
                Individual candidate = remainingCandidates.get((int) (remainingCandidates.size() * Math.random()));
                remainingCandidates.remove(candidate);
                return candidate;
            };

            fillListUpTo(selectedIndividuals, (int) (populationSize*rankRandomRation), rankStrategy);
            fillListUpTo(selectedIndividuals, populationSize, shuffleStrategy);

            return selectedIndividuals;
        };
    }

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
                list.add(supplier.get());
        }
    }
}