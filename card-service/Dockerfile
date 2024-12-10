FROM maven:3.9-eclipse-temurin-17-focal as build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine as builder
WORKDIR /app
COPY --from=build /app/target/*.jar /app/application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/dependencies/ ./dependencies/
COPY --from=builder /app/spring-boot-loader/ ./spring-boot-loader/
COPY --from=builder /app/snapshot-dependencies/ ./snapshot-dependencies/
COPY --from=builder /app/application/ ./application/
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]


