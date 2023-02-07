#FROM maven:3-jdk-11-slim as builder
##copy source to the workdir of builder stage
#ADD . /code/
##solving permission error to do npm build
#RUN chown -R 0 /code/
## build and package the project using maven skiping the test.
#RUN mvn -f /code package -Dmaven.test.skip=true
## move the .jar artifact to root path for simplicity.
#RUN mv /code/target/*.jar /app.jar

FROM adoptopenjdk:11-jre-hotspot
#expose the angular port.
EXPOSE 8083
#execute the service
COPY target/*.jar /app.jar
CMD java -jar /app.jar
# get the jar from builder stage
#COPY --from=builder /app.jar .
