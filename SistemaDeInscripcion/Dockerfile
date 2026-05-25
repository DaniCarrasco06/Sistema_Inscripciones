
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

COPY Wallet_D15AX99A1ECXJR50 /app/wallet

ENV DB_URL="jdbc:oracle:thin:@d15ax99a1ecxjr50_high?TNS_ADMIN=/app/wallet"
ENV DB_USERNAME=""
ENV DB_PASSWORD=""

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]