package com.chill.talkies.error;

import lombok.AllArgsConstructor;

import static java.text.MessageFormat.format;

@AllArgsConstructor
public class MovieNotFoundException extends RuntimeException {
    private String name;

    @Override
    public String getMessage() {
        return format("Movie with id ''{0}'' was not found", name);
    }
}
