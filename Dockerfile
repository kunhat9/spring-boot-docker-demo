FROM maven:3.8.2-jdk-8
RUN mkdir /demoSpring2hour
WORKDIR /demoSpring2hour
COPY . .
EXPOSE 8080
CMD ["mvn","spring-boot:run"]
