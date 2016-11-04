package de.baleipzig.genome;


import de.baleipzig.configuration.GenomeConfig;

public class IntermediateRecombinationGenome extends DoubleGenome {

    public IntermediateRecombinationGenome(GenomeConfig config) {
        super(config);
    }

    @Override
    public Genome<Double> recombine(Genome<Double> male) {

        IntermediateRecombinationGenome childGenome = new IntermediateRecombinationGenome(this.config);

        for(int pos = 0; pos < this.getGenomeLength(); pos++){
            double averageAllele = (this.getAllele(pos)+male.getAllele(pos))/2;
            childGenome.setAllele(pos, averageAllele);
        }

        return childGenome;
    }
}