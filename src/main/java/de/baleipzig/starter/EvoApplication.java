package de.baleipzig.starter;

import de.baleipzig.results.FileDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvoApplication {

    private final FileDataGenerator fileDataGenerator;

    public void start() {

        fileDataGenerator.generateData(null);
    }
}