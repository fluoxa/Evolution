package de.baleipzig.genome;

public interface Genome<T> {

    int getGenomeLength();

    T getAllele(int pos);
    void setAllele(int pos, T val);
}
