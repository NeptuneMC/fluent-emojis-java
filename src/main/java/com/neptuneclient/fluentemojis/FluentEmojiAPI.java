package com.neptuneclient.fluentemojis;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Optional;

public class FluentEmojiAPI {

    private static final String EMOJI_BASE_URL = "https://raw.githubusercontent.com/microsoft/fluentui-emoji/main/assets/%s/%s/%s_%s.%s";

    private static Logger logger = LoggerFactory.getLogger("FluentEmoji");

    private static Optional<File> cacheDirectory = Optional.empty();

    public static void setCustomLogger(Logger customLogger) {
        if (customLogger != null)
            logger = customLogger;
    }

    public static void setCacheDirectory(File directory) {
        if (!directory.isDirectory()) {
            logger.error("The provided cache directory is either not a directory or doesn't exist. No files will be cached!");
            return;
        }
        cacheDirectory = Optional.of(directory);
    }

    public static Emoji downloadEmoji(String id, EmojiStyle style) {
        return downloadEmoji(id, style, 256);
    }

    public static Emoji downloadEmoji(String id, EmojiStyle style, int resolution) {
        if (!id.matches("[_a-z0-9\\d]+")) {
            logger.error("The emoji identifier is invalid! Please follow this regular expression: [_a-z0-9\\d]+");
            return null;
        }
        if (resolution < 1 || resolution > 256) {
            logger.error("The resolution of an emoji must be between 1 and 256 pixels!");
            return null;
        }

        String urlString = String.format(
                EMOJI_BASE_URL,
                Utils.capitalizeEmojiID(id),
                style.getCapitalizedStyleIdentifier(),
                id,
                style.getStyleIdentifier(),
                style == EmojiStyle.THREE_DIMENSIONAL ? "png" : "svg"
        );

        try (InputStream stream = URI.create(urlString).toURL().openStream()) {
            BufferedImage image = ImageIO.read(stream);

            PNGTranscoder transcoder = new PNGTranscoder();
            //transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, resolution);
            //transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, resolution);

            if (cacheDirectory.isPresent()) {
                File cacheFile = new File(cacheDirectory.get() + File.separator + id + ".png");
                if (!cacheFile.exists())
                    cacheFile.createNewFile();

                try (FileOutputStream fileOutput = new FileOutputStream(cacheFile)) {
                    transcoder.writeImage(image, new TranscoderOutput(fileOutput));
                }
            }

            return new Emoji(style, image);
        } catch (IllegalArgumentException | MalformedURLException e) {
            logger.error("Could not get proper image URL for id: \"" + id + "\"");
            logException(e);
            return null;
        } catch (IOException e) {
            logger.error("An error occurred while trying to download \"" + id + "\"");
            logException(e);
            return null;
        } catch (TranscoderException e) {
            logger.error("An error occurred while transcoding image with id: \"" + id + "\"");
            logException(e);
            return null;
        }
    }

    private static void logException(Exception e) {
        logger.error(e.getClass().getSimpleName() + ": " + e.getMessage());
    }

}
