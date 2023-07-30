FROM openjdk:17 

WORKDIR /app
VOLUME /app/data
EXPOSE 8080

COPY target/grow_api-0.0.1-SNAPSHOT.jar app.jar
COPY empty-db.sqlite data/db.sqlite

CMD ["java", "-jar", "app.jar"]