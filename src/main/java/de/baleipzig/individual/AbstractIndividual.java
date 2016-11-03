package de.baleipzig.individual;

import de.baleipzig.encoding.Genome;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@ToString
public abstract class AbstractIndividual<T extends Genome> implements Individual<T> {

    @Autowired
    protected T genome;
}
