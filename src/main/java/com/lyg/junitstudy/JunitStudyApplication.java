package com.lyg.junitstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JunitStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JunitStudyApplication.class, args);
    }
}
