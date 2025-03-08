package com.heating_report.heating_report;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class HeatingReportApplication {
	// Статический блок для загрузки переменных из .env
	static {
		// Задаем путь к .env в папке deploy_regent_server
		String deployEnvPath = "/root/deploy_regent_server/.env"; // Путь, если .env в deploy_regent_server
		String projectEnvPath = ".env"; // Путь, если .env в корне проекта

		// Переменная для загрузки .env из одного из двух путей
		Dotenv dotenv = null;

		// Сначала пытаемся загрузить из deploy_regent_server
		if (Files.exists(Paths.get(deployEnvPath))) {
			dotenv = Dotenv.configure().directory("/root/deploy_regent_server").load();
		}
		// Если файл не найден, пытаемся загрузить из корня проекта
		if (dotenv == null || dotenv.entries().isEmpty()) {
			dotenv = Dotenv.configure().load();
		}

		// Загружаем переменные окружения из найденного .env
		if (dotenv != null) {
			dotenv.entries().forEach(entry -> {
				System.setProperty(entry.getKey(), entry.getValue());
			});
		} else {
			throw new RuntimeException(".env file not found in both paths");
		}
	}

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
