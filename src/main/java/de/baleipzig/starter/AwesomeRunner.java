package de.baleipzig.starter;

import de.baleipzig.configuration.EvoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("de.baleipzig")
@EnableAutoConfiguration
@SpringBootApplication
public class AwesomeRunner implements CommandLineRunner {

    @Autowired
    EvoConfig config;

    @Override
    public void run(String... args) {
        System.out.println(config);

        if (args.length > 0 && args[0].equals("exitcode")) {
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AwesomeRunner.class, args);
    }
}
