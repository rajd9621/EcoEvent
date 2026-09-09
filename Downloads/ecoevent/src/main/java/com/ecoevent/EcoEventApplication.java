package com.ecoevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * EcoEvent - Smart Sustainable Event Management System
 * 
 * A Spring Boot application integrating UN SDG 12 (Responsible Consumption
 * and Production) into every aspect of event management.
 * 
 * Run with: mvn spring-boot:run
 * Opens at: http://localhost:8080
 */
@SpringBootApplication
public class EcoEventApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoEventApplication.class, args);
    }
}
