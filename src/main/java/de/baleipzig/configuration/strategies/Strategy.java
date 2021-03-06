package de.baleipzig.configuration.strategies;

import de.baleipzig.genome.Genome;
import de.baleipzig.individual.Individual;
import de.baleipzig.individual.Parents;
import de.baleipzig.population.Population;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Data
@RequiredArgsConstructor
public class Strategy {
    @Getter @Setter
    private static double parentSelectionRatio = 0;

    private final Function<Population, Parents> parentSelection;
    private final BiFunction<List<Individual>, Integer, List<Individual>> naturalSelection;
    private final Function<Individual<Genome<Double>>, Individual<Genome<Double>>> mutation;
    private final GenomeRecombination.GenomeRecombinationFunction genomeCrossing;
}
