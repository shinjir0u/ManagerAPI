FROM maven:3.9.5-eclipse-temurin-21 AS DEPENDENCIES 
WORKDIR /opt/app
COPY pom.xml .
COPY web/pom.xml web/
COPY service/pom.xml service/
COPY core/pom.xml core/
COPY github_api/pom.xml github_api/
COPY persistence/pom.xml persistence/
COPY security/pom.xml security/
RUN mvn dependency:go-offline -B 

FROM maven:3.9.5-eclipse-temurin-21 AS BUILDER
WORKDIR /opt/app
COPY --from=DEPENDENCIES /opt/app /opt/app/
COPY --from=DEPENDENCIES /root/.m2 /root/.m2
COPY web/src /opt/app/web/src/
COPY service/src /opt/app/service/src/
COPY core/src /opt/app/core/src/
COPY github_api/src /opt/app/github_api/src/
COPY persistence/src /opt/app/persistence/src/
COPY security/src /opt/app/security/src/
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /opt/app
COPY --from=BUILDER /opt/app/web/target/*.jar /opt/app/app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]