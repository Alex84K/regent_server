FROM maven:3.8.1-openjdk-17 AS build

# Копируем проект и собираем его
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -DskipTests

# Используйте образ для выполнения
FROM openjdk:17-jdk-slim
COPY --from=build /usr/src/app/target/*.jar /usr/app/report.jar
CMD ["java", "-jar", "/usr/app/report.jar"]
