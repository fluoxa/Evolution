package de.baleipzig.individual;

import de.baleipzig.genome.Genome;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

@ToString
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public abstract class AbstractIndividual<T extends Genome> implements Individual<T> {

    private final Function<T, Double> fitnessFunction;

    @Autowired
    protected T genome;

    @Override
    public Double getFitness() {

        return fitnessFunction.apply(genome);
    }

    @Override
    public int compareTo(Individual<T> o) {

        return this.getFitness().compareTo(o.getFitness());
    }
}
