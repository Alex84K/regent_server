package com.heating_report.heating_report;

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
		System.out.println(1234);
	}

	public static void main(String[] args) {
		SpringApplication.run(HeatingReportApplication.class, args);
	}

}
