package com.openclassrooms.safetynet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Safetynet application.
 * This class serves as the bootstrap for the Spring Boot application, 
 * starting the application context and launching the application.
 */
@SpringBootApplication
public class SafetynetApplication {

    /**
     * The main method, used to launch the Safetynet application.
     * It runs the Spring Boot application by invoking SpringApplication.run() method.
     *
     * @param args the command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(SafetynetApplication.class, args);
    }
}