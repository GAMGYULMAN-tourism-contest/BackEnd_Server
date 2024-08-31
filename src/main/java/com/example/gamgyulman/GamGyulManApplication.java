package com.example.gamgyulman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GamGyulManApplication {

    public static void main(String[] args) {
        SpringApplication.run(GamGyulManApplication.class, args);
    }

}
