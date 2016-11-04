package de.baleipzig.individual;

import lombok.Data;

@Data
public class Parents<T extends Individual> {

    private T father;
    private T mother;
}
