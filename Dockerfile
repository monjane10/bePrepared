# Etapa de construção
FROM ubuntu:latest AS build

# Atualize os repositórios e instale dependências necessárias
RUN apt-get update && apt-get install -y wget tar

# Baixe e instale o OpenJDK 21 manualmente
RUN wget https://download.java.net/java/early_access/jdk21/26/GPL/openjdk-21-ea+26_linux-x64_bin.tar.gz
RUN tar -xzf openjdk-21-ea+26_linux-x64_bin.tar.gz
RUN mv jdk-21 /usr/local/

# Defina o JAVA_HOME e adicione o binário do JDK ao PATH
ENV JAVA_HOME=/usr/local/jdk-21
ENV PATH=$JAVA_HOME/bin:$PATH

# Instale o Maven
RUN apt-get install -y maven

# Copie o código fonte e construa o projeto
COPY . .
RUN mvn clean install

# Etapa de execução
FROM ubuntu:latest

# Copie o OpenJDK 21 do estágio de construção
COPY --from=build /usr/local/jdk-21 /usr/local/jdk-21
ENV JAVA_HOME=/usr/local/jdk-21
ENV PATH=$JAVA_HOME/bin:$PATH

# Copie o JAR construído
COPY --from=build /target/*.jar ./beprepared.jar

# Exponha a porta e defina o ponto de entrada
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "beprepared.jar"]