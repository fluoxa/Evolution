package de.baleipzig.encoding;

import java.util.Vector;

public class RealGenome implements Genome<Double> {

    private final Vector<Double> genes;

    public RealGenome(int numberOfGenes) {

        genes = new Vector<>(numberOfGenes);
    }

//    public Genome recombine(Genome male) {
//
//
//    }
}
