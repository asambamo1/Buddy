package com.randomappsinc.blanknavigationdrawer.API.Models;

import java.util.List;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public class SuggestionsEvent {
    private List<Suggestion> suggestionList;

    public SuggestionsEvent(List<Suggestion> suggestionList) {
        this.suggestionList = suggestionList;
    }

    public List<Suggestion> getSuggestionList() {
        return suggestionList;
    }
}
