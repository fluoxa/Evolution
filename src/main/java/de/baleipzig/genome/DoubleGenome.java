package de.baleipzig.genome;

import de.baleipzig.configuration.GenomeConfig;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Random;
import java.util.Vector;

@ToString
public class DoubleGenome implements Genome<Double> {

    @Getter
    protected final Vector<Double> alleles;

    protected final GenomeConfig config;

    private static final Random random = new Random();

    public DoubleGenome(GenomeConfig config) {

        this.config = config;

        alleles = new Vector<>(config.getNumberOfGenes());
        for (int pos = 0; pos < config.getNumberOfGenes(); pos++) {
            alleles.add(config.getLowerBound() + random.nextDouble()*(config.getUpperBound()-config.getLowerBound()));
        }
    }

    @Override
    public int getGenomeLength(){

        return alleles.size();
    }

    @Override
    public Double getAllele(int pos) {

        if(pos < 0 || pos > alleles.size()) {
            throw new RuntimeException("getAllele: position out of Range");
        }

        return alleles.elementAt(pos);
    }

    @Override
    public void setAllele(int pos, Double value) {

        if(pos < 0 || pos > alleles.size()) {
            throw new RuntimeException("setAllele: position out of Range");
        }

        alleles.set(pos, value);
    }

    @Override
    public void setAlleles(List<Double> newAlleles) {

        if(newAlleles.size() != alleles.size()) {
            throw new RuntimeException("setAlleles: genomeSize unequal to new list size");
        }

        alleles.clear();
        alleles.addAll(newAlleles);
    }
}
