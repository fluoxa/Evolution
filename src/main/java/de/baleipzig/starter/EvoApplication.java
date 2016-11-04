package de.baleipzig.starter;

import de.baleipzig.population.Population;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvoApplication {

    private final ApplicationContext context;
    private final Population population;

    public void start() {

        population.populate();


    }
}
