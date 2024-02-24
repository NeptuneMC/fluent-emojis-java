import com.neptuneclient.fluentemojis.EmojiStyle;
import com.neptuneclient.fluentemojis.FluentEmojiAPI;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File cache = new File("emojis");
        if (!cache.exists())
            cache.mkdirs();

        FluentEmojiAPI.setCacheDirectory(cache);
        FluentEmojiAPI.downloadEmoji("ice_cream", EmojiStyle.THREE_DIMENSIONAL);
    }

}
