# Utiliza una imagen base de Java 17 con Alpine Linux para una imagen más pequeña
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el pom.xml y ejecuta Maven para construir el JAR
COPY pom.xml ./
RUN mvn package

# Copia el JAR generado al directorio de trabajo
COPY target/*.jar app.jar

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java","-jar","/app/app.jar"]