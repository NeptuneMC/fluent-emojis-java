# Fluent Emoji Java
A web scraper for [Microsoft's FluentUI emojis](https://github.com/microsoft/fluentui-emoji) used in Neptune client.

### Usage
```java
File cache = new File("emojis");
if (!cache.exists())
    cache.mkdirs();

FluentEmojiAPI.setCacheDirectory(cache);    // set a cache directory to save emoji PNGs
FluentEmojiAPI.downloadEmoji("ice_cream", EmojiStyle.THREE_DIMENSIONAL);    // download emoji from web
```

### TODO
* add support for all emoji styles
* add more examples
* create package_info.java
