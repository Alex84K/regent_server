package com.heating_report.heating_report;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class HeatingReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeatingReportApplication.class, args);
	}
}


/*package com.heating_report.heating_report;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HeatingReportApplication {
	// Статический блок для загрузки переменных из .env
	static {
		Dotenv dotenv = Dotenv.configure().load();
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(HeatingReportApplication.class, args);
	}
}*/
