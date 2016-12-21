package com.brainmatics.pelatihan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(
  basePackageClasses = { AplikasiPelatihanApplication.class, Jsr310JpaConverters.class }
)
public class AplikasiPelatihanApplication {

	public static void main(String[] args) {
		SpringApplication.run(AplikasiPelatihanApplication.class, args);
	}
}
