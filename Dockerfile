# FROM ubuntu:latest AS build 

# RUN apt-get update
# RUN apt-get install openjdk-17-jdk -y

# COPY . . 

# RUN apt-get install mavan -y 

# RUN mvn clean install

# EXPOSE 8080

# COPY --from=build /target/todolist-1.0.0.jar app.jar

# ENTRYPOINT [ "java", "-jar", "app.jar"]

# Use a imagem base que possui o Java e o Gradle
FROM adoptopenjdk:17-jre-hotspot AS build

# Copie os arquivos de origem para o contêiner
WORKDIR /app
COPY . .

# Construa o aplicativo com o Gradle
RUN ./gradlew build

# Estágio de construção concluído, agora crie a imagem final
FROM adoptopenjdk:17-jre-hotspot

# Copie o arquivo JAR construído a partir do estágio anterior
COPY --from=build /app/build/libs/your-app.jar /app.jar

# Exponha a porta em que o aplicativo será executado
EXPOSE 8080

# Comando de entrada para iniciar o aplicativo
ENTRYPOINT ["java", "-jar", "/app.jar"]