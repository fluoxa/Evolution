package de.baleipzig;

import de.baleipzig.starter.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("de.baleipzig")
@EnableAutoConfiguration
@SpringBootApplication
public class App implements CommandLineRunner {

    @Override
    public void run(String... args) {

        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new RuntimeException("wrong arguments");
        }

        Application appl = new Application();
        appl.start();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}