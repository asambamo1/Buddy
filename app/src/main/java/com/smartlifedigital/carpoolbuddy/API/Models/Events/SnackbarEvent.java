package com.smartlifedigital.carpoolbuddy.API.Models.Events;

/**
 * Created by alexanderchiou on 12/25/15.
 */
public class SnackbarEvent {
    private String screen;
    private String message;

    public SnackbarEvent(String screen, String message) {
        this.screen = screen;
        this.message = message;
    }

    public String getScreen() {
        return screen;
    }

    public String getMessage() {
        return message;
    }
}
