package com.yerimen.user;

public class UserInformation {

    private final String username;
    private final String character;

    public UserInformation(String username, String character) {
        this.username = username;
        this.character = character;
    }

    public String getCharacter() {
        return character;
    }

    public String getUsername() {
        return username;
    }
}
