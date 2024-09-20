package dev.luan.rest;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log
public class Application {

    public static MongoClient mongoClient;
    public static MongoDatabase mongoDatabase;

    public static void main(String[] args) {
        try {

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        SpringApplication.run(Application.class, args);
    }
}
