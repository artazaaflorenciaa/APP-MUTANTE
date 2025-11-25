# Usa la imagen de Ubuntu como base
FROM ubuntu:latest AS build

# Actualiza e instala Java
RUN apt-get update && apt-get install openjdk-17-jdk -y

# Copia el proyecto al contenedor
COPY . .

# Cambia los permisos de gradlew para que sea ejecutable
RUN chmod +x gradlew

# Ejecuta el comando para construir el archivo JAR
RUN ./gradlew bootJar --no-daemon

# Usa la imagen de OpenJDK como base para ejecutar el JAR
FROM eclipse-temurin:17-jdk-alpine

# Copia el archivo JAR generado al contenedor y lo renombra
COPY --from=build build/libs/appMutante-*.jar app.jar


# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]
