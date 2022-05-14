#FROM maven:3.8.5-amazoncorretto-17
#WORKDIR /local/legacy
#COPY . .
#RUN mvn install -f clean package

FROM amazoncorretto:17-alpine3.14
EXPOSE 8080
ADD target/legacyBankingAPI-0.0.1-SNAPSHOT.jar legacyBankingAPI.jar
ENTRYPOINT ["java","-jar","legacyBankingAPI.jar"]
