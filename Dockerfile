# Используем Maven для сборки
FROM maven:3.8.1-openjdk-17 AS build

# Копируем весь проект внутрь контейнера
COPY . /usr/src/app
WORKDIR /usr/src/app

# Собираем приложение (без тестов для ускорения)
RUN mvn clean package -DskipTests

# Используем легковесный OpenJDK для запуска
FROM openjdk:17-jdk-slim

# Создаем рабочую директорию
WORKDIR /usr/app

# Копируем скомпилированный JAR-файл
COPY --from=build /usr/src/app/target/*.jar /usr/app/report.jar

# Пример использования переменных окружения (установим переменные для конфигурации)
# Устанавливаем переменные окружения, которые могут быть использованы в вашем приложении
#ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
#ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
#ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_MAIL_USERNAME=${SPRING_DATASOURCE_MAIL_USERNAME}
ENV SPRING_DATASOURCE_MAIL_PWD=${SPRING_DATASOURCE_MAIL_PWD}
ENV SPRING_DATASOURCE_MAIL_HOST=${SPRING_DATASOURCE_MAIL_HOST}

# Запускаем приложение
CMD ["java", "-jar", "/usr/app/report.jar"]
