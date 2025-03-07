# Etapa de construção (builder)
FROM maven:3.9.9-amazoncorretto-21-al2023 AS builder
WORKDIR /app

# Copia apenas o arquivo pom.xml para baixar as dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar o código-fonte
COPY src ./src

# Compilar o projeto e gerar o JAR
RUN mvn clean package -DskipTests

# Etapa de produção
FROM amazoncorretto:21-al2-full AS runtime
WORKDIR /app

# Copiar o JAR gerado na etapa anterior
COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

# Expor a porta do aplicativo
EXPOSE 8080

# Configurar o ponto de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]