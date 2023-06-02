FROM openjdk:17-jdk
WORKDIR /app
COPY target/fee-payment-microservice-0.0.1-SNAPSHOT.jar fee-payment-microservice.jar
EXPOSE 9090
CMD ["java", "-jar", "fee-payment-microservice.jar"]