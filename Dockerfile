FROM eclipse-temurin:17-jdk AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el jar generado por Maven o Gradle
COPY target/cleanview-backend-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto (Render lo redirige automáticamente)
EXPOSE 8080

# Comando de ejecución
CMD ["java", "-jar", "app.jar"]
