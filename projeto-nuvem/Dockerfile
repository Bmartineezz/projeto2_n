# Estágio 1: Build da aplicação
FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar arquivos do Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# Baixar dependências
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Estágio 2: Imagem de execução
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expor porta da aplicação
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]