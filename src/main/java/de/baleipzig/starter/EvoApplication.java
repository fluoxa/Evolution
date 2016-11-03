package de.baleipzig.starter;

import de.baleipzig.configuration.EvoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvoApplication {

    private final EvoConfig config;

    public void start() {
        System.out.println(config);
    }
}
