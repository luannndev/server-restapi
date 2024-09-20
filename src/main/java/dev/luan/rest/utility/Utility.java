package dev.luan.rest.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utility {

    public String removeMinecraftColorCodes(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("<[^>]+>", "").trim();
    }
}