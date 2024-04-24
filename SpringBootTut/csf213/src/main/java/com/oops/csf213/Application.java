package com.oops.csf213;

import com.oops.csf213.run.Location;
import com.oops.csf213.run.Run;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class Application {

    // create a logger like this. logger from slf4j (default logger in Spring Boot)
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("Application started"); // use logger like this and we can change log levels as well

        /*
         Bean -> instance of class + some metadata
         Bean -> instance of class that spring container (the container of objects) is managing
         For this to work, the class should be in the main package
         commenting this out, not needed in real. key point -> always store code in main package, not default, not elsewhere for Spring's dependency injection or IOC to work nicely

         ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
         WelcomeMessage welMsg = (WelcomeMessage) context.getBean("welcomeMessage");
         System.out.println(welMsg);
        */
    }

    // this run after the application has started & the context has been created
    // context -> (the container for all beans in the system)
    @Bean
    CommandLineRunner runner() {
        return args -> {
            Run run = new Run(1, "first rune", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), 120, Location.OUTDOOR);
            log.info("Run: " + run);
        };
    }

}
