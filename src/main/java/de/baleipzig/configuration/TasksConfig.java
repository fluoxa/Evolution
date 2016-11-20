package de.baleipzig.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(locations="classpath:Application.yml", prefix="de.baleipzig.evoconfig.tasksconfig")
public class TasksConfig {

    private List<Integer> consideredGriewankDimensions = new ArrayList<>();
    private int numberOfSamplingPoints;
    private String strategy;
    private String outputPath;
    private int runsPerDataPoint;
}
