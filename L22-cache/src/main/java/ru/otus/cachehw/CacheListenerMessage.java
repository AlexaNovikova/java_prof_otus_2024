package ru.otus.cachehw;

import lombok.Getter;

@Getter
public enum CacheListenerMessage {
    KEY_VALUE_ADDED("New key-value added"),
    KEY_VALUE_REPLACED("Key-value replaced"),
    KEY_REMOVED("Key removed"),
    KEY_GET("Key get"),
    KEY_NOT_FOUND("Key not found");

    private final String message;

    CacheListenerMessage(String message) {
        this.message = message;
    }
}
