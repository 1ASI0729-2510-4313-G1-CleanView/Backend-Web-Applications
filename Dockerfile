# Etapa 1: Build con Maven y JDK
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copia los archivos necesarios para compilar
COPY pom.xml .
COPY src ./src

# Ejecuta el build del proyecto sin tests
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copia el jar compilado desde la etapa de build
COPY --from=build /app/target/cleanview-backend-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto que usa Spring Boot
EXPOSE 8080

# Comando para ejecutar la app
CMD ["java", "-jar", "app.jar"]
