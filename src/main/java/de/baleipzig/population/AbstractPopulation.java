package de.baleipzig.population;

import de.baleipzig.configuration.PopulationConfig;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@RequiredArgsConstructor
public abstract class AbstractPopulation<T> implements Population {

    protected final PopulationConfig config;

    protected List<T> population = new ArrayList<>();

    protected abstract Population breed();
    protected abstract Population naturalSelection();
}
