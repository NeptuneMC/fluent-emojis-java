package com.neptuneclient.fluentemojis;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.image.BufferedImage;

@Getter
@AllArgsConstructor
public class Emoji {

    private EmojiStyle style;

    private BufferedImage image;

}
