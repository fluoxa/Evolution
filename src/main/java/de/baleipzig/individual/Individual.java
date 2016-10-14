package de.baleipzig.individual;

import de.baleipzig.encoding.Genome;

public interface Individual<T extends Genome> {
    Individual<T> mateWith(Individual<T> individual);

    Individual<T> mutate();
}
