package com.neptuneclient.fluentemojis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmojiStyle {

    THREE_DIMENSIONAL("3d", "3D"),
    COLOR("color", "Color"),
    FLAT("flat", "Flat"),
    HIGH_CONTRAST("high_contrast", "High Contrast");

    private String styleIdentifier;

    private String capitalizedStyleIdentifier;


}
