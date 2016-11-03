package de.baleipzig.encoding;


import de.baleipzig.configuration.GenomeConfig;

public class IntermediateRecombGenome extends RealGenome {

    public IntermediateRecombGenome(GenomeConfig config) {
        super(config);
    }

    @Override
    public Genome<Double> recombine(Genome<Double> male) {

        IntermediateRecombGenome childGenome = new IntermediateRecombGenome(this.config);

        for(int pos = 0; pos < this.getGenomeLength(); pos++){
            double averageAllele = (this.getAllele(pos)+male.getAllele(pos))/2;
            childGenome.setAllele(pos, averageAllele);
        }

        return childGenome;
    }
}