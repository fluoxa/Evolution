package de.baleipzig.encoding;

import de.baleipzig.configuration.GenomeConfig;
import lombok.ToString;

import java.util.Random;
import java.util.Vector;

@ToString
public abstract class RealGenome implements Genome<Double> {

    protected final Vector<Double> genes;

    protected final GenomeConfig config;

    private static final Random random = new Random();

    public RealGenome(GenomeConfig config) {

        this.config = config;

        genes = new Vector<>(config.getNumberOfGenes());
        for (int pos = 0; pos < config.getNumberOfGenes(); pos++) {
            genes.add(config.getLowerBound() + random.nextDouble()*(config.getUpperBound()-config.getLowerBound()));
        }
    }

    @Override
    public int getGenomeLength(){

        return genes.size();
    }

    @Override
    public Double getAllele(int pos) {

        if(pos < 0 || pos > genes.size()) {
            throw new RuntimeException("getAllele: position out of Range");
        }

        return genes.elementAt(pos);
    }

    @Override
    public void setAllele(int pos, Double value) {

        if(pos < 0 || pos > genes.size()) {
            throw new RuntimeException("setAllele: position out of Range");
        }

        genes.set(pos, value);
    }
}
