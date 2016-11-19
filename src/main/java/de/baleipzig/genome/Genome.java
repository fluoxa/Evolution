package de.baleipzig.genome;

import java.util.List;

public interface Genome<T> {

    int getGenomeLength();

    List<T> getAlleles();

    T getAllele(int pos);
    void setAllele(int pos, T val);

    void setAlleles(List<T> alleles);
}
