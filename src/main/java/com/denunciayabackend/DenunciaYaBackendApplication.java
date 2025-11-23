package com.denunciayabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DenunciaYaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DenunciaYaBackendApplication.class, args);
    }

}