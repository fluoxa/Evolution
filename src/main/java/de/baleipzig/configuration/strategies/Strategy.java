package de.baleipzig.configuration.strategies;

import de.baleipzig.individual.Parents;
import de.baleipzig.population.Population;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Data
@RequiredArgsConstructor
public class Strategy {
    private final Function<Population, Parents> parentSelection;
    private final Function<Population,Population> naturalSelection;
}
