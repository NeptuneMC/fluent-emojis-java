package com.neptuneclient.fluentemojis;

import java.util.Locale;

public class Utils {

    public static String capitalizeEmojiID(String id) {
        String capitalizedId = id.replace("_", "%20");
        return capitalizedId.substring(0, 1).toUpperCase(Locale.ENGLISH) + capitalizedId.substring(1);    // Make the first letter uppercase
    }

}
