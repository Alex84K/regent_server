# Используем Maven для сборки
FROM maven:3.8.1-openjdk-17 AS build

# Указываем рабочую директорию
WORKDIR /usr/src/app

# Копируем все файлы проекта в контейнер
COPY . /usr/src/app

# Выполняем сборку проекта с Maven
RUN mvn clean package -DskipTests

# Используем OpenJDK для выполнения
FROM openjdk:17-jdk-slim

# Копируем собранный артефакт из предыдущего шага
COPY --from=build /usr/src/app/target/*.jar /usr/app/report.jar

# Пример использования переменных окружения (установим переменные для конфигурации)
# Устанавливаем переменные окружения, которые могут быть использованы в вашем приложении
ENV SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
ENV SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
ENV SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}

# Запускаем приложение
CMD ["java", "-jar", "/usr/app/report.jar"]
