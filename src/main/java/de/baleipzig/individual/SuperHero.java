package de.baleipzig.individual;

import de.baleipzig.configuration.strategies.Strategy;
import de.baleipzig.genome.Genome;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.function.Function;

@ToString
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SuperHero<T extends Genome> implements Individual<T> {

    @Autowired
    private ApplicationContext context;

    private final Function<T, Double> fitnessFunction;
    private final Strategy strategy;

    @Autowired
    @Getter
    @Setter
    protected T genome;

    @Getter
    protected int age = 0;

    @Override
    public void mutate() {

        strategy.getMutation().accept(this);
    }

    @Override
    public Double getFitness() {

        return fitnessFunction.apply(genome);
    }

    @Override
    public void incrementAge() {
        age++;
    }

    @Override
    public int compareTo(Individual<T> o) {

        return this.getFitness().compareTo(o.getFitness());
    }

    @Override
    public Individual<T> mateWith(Individual<T> father) {

        Individual child = context.getBean(Individual.class);
        strategy.getGenomeCrossing().recombine(this.getGenome(), father.getGenome(), child.getGenome());

        return child;
    }
}