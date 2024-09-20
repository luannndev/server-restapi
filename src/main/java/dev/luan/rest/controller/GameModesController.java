package dev.luan.rest.controller;

import com.mongodb.client.model.Filters;
import dev.luan.rest.Application;
import dev.luan.rest.gamemode.GameMode;
import dev.luan.rest.utility.Utility;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class GameModesController {

    final Map<String, GameMode> gameModes = new HashMap<>();


    public GameModesController() {
        //TODO: ADD BANNER PNG
        gameModes.put("Undefined", new GameMode("Undefined", "Undefined", "Undefined",
                List.of("Undefined")));

        this.defineGameModes();


    }

    private void defineGameModes() {
        Application.mongoDatabase.getCollection("lobby_navigator_games").find().forEach(document -> {
            String gameModeName = document.getString("_id");
            String descriptionTranslationKey = document.getString("descriptionTranslationKey");

            Document translationsDoc = Application.mongoDatabase.getCollection("translations").find(Filters.eq("_id", descriptionTranslationKey)).first();

            Document translations = (translationsDoc != null) ? (Document) translationsDoc.get("translations") : null;
            String enUsTranslation = (translations != null) ? translations.getString("en_US") : "Undefined";

            GameMode gameMode = new GameMode();
            gameMode.setGameName(gameModeName);
            gameMode.setDescription(Utility.removeMinecraftColorCodes(enUsTranslation));
            gameMode.setTags(document.getList("tags", String.class).stream().map(s -> s.replaceAll("_", " ")).toList());
            //TODO: CREATE IMAGE LINK FOR GAMEMODES
            gameMode.setImageUrl("" + gameModeName.toLowerCase().replaceAll(" ", "-") + "-banner.png");

            gameModes.put(gameModeName.replaceAll(" ", "_"), gameMode);
        });
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/game-modes")
    @ResponseBody
    public Map<String, GameMode> getGameModes() {
        return gameModes;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/game-mode")
    public GameMode getSpecificGameMode(@RequestParam(value = "gameName") String gameName) {
        return this.gameModes.getOrDefault(gameName, gameModes.get("Undefined"));
    }
}