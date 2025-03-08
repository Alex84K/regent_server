FROM maven:3.8.1-openjdk-17 AS build

# Устанавливаем рабочую директорию перед копированием
WORKDIR /usr/src/app

# Копируем файлы проекта
COPY . .

# Проверяем, что файлы скопированы
RUN ls -la

# Сборка проекта
RUN mvn clean package -DskipTests

# Используем минимальный образ для запуска
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /usr/src/app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
