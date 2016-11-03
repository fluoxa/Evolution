package de.baleipzig.encoding;

import java.util.Vector;

public class RealGenome implements Genome {

    private final Vector<Double> genes;

    public RealGenome(int numberOfGenes) {

        genes = new Vector<>(numberOfGenes);
    }
}
