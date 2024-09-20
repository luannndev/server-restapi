package dev.luan.rest.controller;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import dev.luan.rest.Application;
import dev.luan.rest.utility.UUIDUtility;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class NetworkProfileController {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/network-profile")
    public ResponseEntity<Document> getNetworkProfileByUniqueId(@RequestParam(value = "uniqueId") String uniqueId) {

        UUID uuid;

        try {
            uuid = UUID.fromString(uniqueId);
        } catch (Exception exception) {
            Document errorDocument = new Document("error", "The given uniqueId does not match the correct UUID regex");
            return new ResponseEntity<>(errorDocument, HttpStatus.BAD_REQUEST);
        }

        Document document = Application.mongoDatabase.getCollection("network_profiles")
                .find(Filters.eq("_id", uuid)).first();

        if (document == null) {
            Document errorDocument = new Document("error", "Profile does not exist or is null?");
            return new ResponseEntity<>(errorDocument, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(document);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/network-profile/by-name")
    public ResponseEntity<Document> getNetworkProfileByName(@RequestParam(value = "name") String name) {

        UUID uuid = UUIDUtility.getUniqueId(name);

        Document document = Application.mongoDatabase.getCollection("network_profiles")
                .find(Filters.eq("_id", uuid)).first();

        if (document == null) {
            Document errorDocument = new Document("error", "Profile does not exist or is null?");
            return new ResponseEntity<>(errorDocument, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(document);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/network-profile/sort")
    public ResponseEntity<List<Document>> getLastActivePlayers(@RequestParam(value = "field") String field) {
        MongoCollection<Document> collection = Application.mongoDatabase.getCollection("network_profiles");

        List<Document> lastActivePlayers = collection.find()
                .sort(Sorts.descending(field))
                .limit(5)
                .into(new ArrayList<>());


        return ResponseEntity.ok(lastActivePlayers);
    }

}