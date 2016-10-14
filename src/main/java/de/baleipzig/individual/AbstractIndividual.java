package de.baleipzig.individual;

import de.baleipzig.encoding.Genome;

public abstract class AbstractIndividual<T extends Genome> implements Individual<T> {
    protected T genome;
}
