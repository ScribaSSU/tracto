package com.scribassu.tracto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TractoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TractoApplication.class, args);
    }

}
