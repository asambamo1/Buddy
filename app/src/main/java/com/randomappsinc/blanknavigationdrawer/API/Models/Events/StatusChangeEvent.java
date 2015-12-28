package com.randomappsinc.blanknavigationdrawer.API.Models.Events;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class StatusChangeEvent {
    private String newStatus;

    public StatusChangeEvent(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }
}
