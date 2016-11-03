package de.baleipzig.encoding;

public interface Genome<T> {

    int getGenomeLength();

    T getAllele(int pos);
    void setAllele(int pos, T val);

    Genome<T> recombine(Genome<T> male);
}
