package com.piter.importedcars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.piter.importedcars")
public class ImportedCarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImportedCarsApplication.class, args);
	}

}
