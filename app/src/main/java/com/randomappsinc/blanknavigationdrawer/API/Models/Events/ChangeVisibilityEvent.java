package com.randomappsinc.blanknavigationdrawer.API.Models.Events;

/**
 * Created by jman0_000 on 1/14/2016.
 */
public class ChangeVisibilityEvent {
    String response;

    public ChangeVisibilityEvent(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
