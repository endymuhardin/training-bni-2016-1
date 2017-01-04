package com.brainmatics.pelatihan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

@SpringBootApplication
@EntityScan(
        basePackageClasses = {AplikasiPelatihanApplication.class, Jsr310JpaConverters.class}
)
public class AplikasiPelatihanApplication {

    public static void main(String[] args) {
        SpringApplication.run(AplikasiPelatihanApplication.class, args);
    }

    @Bean
    public SpringDataDialect springDataDialect() {
        return new SpringDataDialect();
    }
}
