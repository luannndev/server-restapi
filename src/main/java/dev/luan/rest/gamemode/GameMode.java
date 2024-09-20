package dev.luan.rest.gamemode;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class GameMode {
    String gameName;
    String imageUrl;
    String description;
    List<String> tags;
}