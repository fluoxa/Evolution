package de.baleipzig.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("de.baleipzig")
@EnableAutoConfiguration
@SpringBootApplication
public class EvoRunner implements CommandLineRunner {

    @Autowired
    private EvoApplication application;

    @Override
    public void run(String... args) {

        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new RuntimeException("wrong arguments");
        }

        application.start();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EvoRunner.class, args);
    }
}