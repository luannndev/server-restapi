package dev.luan.rest;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.java.Log;
import org.bson.UuidRepresentation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class Application {

    public static MongoClient mongoClient;
    public static MongoDatabase mongoDatabase;

    public static void main(String[] args) {
        try {
            ConnectionString connectionString = new ConnectionString("mongodb://user:password@localhost:27017/");
            MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .uuidRepresentation(UuidRepresentation.STANDARD)
                    .build();

            mongoClient = MongoClients.create(mongoClientSettings);
            mongoDatabase = mongoClient.getDatabase("restapi");

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        SpringApplication.run(Application.class, args);
    }
}
