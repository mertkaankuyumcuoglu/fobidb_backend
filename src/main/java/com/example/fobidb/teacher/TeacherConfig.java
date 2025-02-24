package com.example.fobidb.teacher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class TeacherConfig {
    @Bean
    CommandLineRunner commandLineRunner(TeacherRepository repository) {
        return args -> {
            Teacher simon = new Teacher(
                "Schubert",
                "Simon",
                "sb",
                "simon.schubert@aloberlin.de",
                45
            );

            Teacher dennis = new Teacher(
                "Pommer",
                "Dennis",
                "pr",
                "dennis.pommer@aloberlin.de",
                50
            );
            repository.saveAll(
                List.of(simon, dennis)
            );
        };
    }
}
