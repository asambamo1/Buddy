package com.randomappsinc.blanknavigationdrawer.API.Models.Events;

import com.randomappsinc.blanknavigationdrawer.API.Models.UserThumbnail;

import java.util.List;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public class UserThumbnailsEvent {
    private String screen;
    private List<UserThumbnail> suggestionList;

    public UserThumbnailsEvent(String screen, List<UserThumbnail> suggestionList) {
        this.screen = screen;
        this.suggestionList = suggestionList;
    }

    public String getScreen() {
        return screen;
    }

    public List<UserThumbnail> getSuggestionList() {
        return suggestionList;
    }
}
