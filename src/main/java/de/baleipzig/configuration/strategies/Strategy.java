package de.baleipzig.configuration.strategies;

import de.baleipzig.genome.Genome;
import de.baleipzig.individual.Individual;
import de.baleipzig.individual.Parents;
import de.baleipzig.population.Population;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Data
@RequiredArgsConstructor
public class Strategy {
    private final Function<Population, Parents> parentSelection;
    private final BiFunction<List<Individual>, Integer, List<Individual>> naturalSelection;
    private final Consumer<Individual<Genome<Double>>> mutation;
    private final GenomeRecombination.GenomeRecombinationFunction genomeCrossing;
}
