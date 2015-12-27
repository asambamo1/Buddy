package com.randomappsinc.blanknavigationdrawer.API.Services;

import com.randomappsinc.blanknavigationdrawer.API.Models.Suggestion;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public interface SuggestionService {
    @GET("/suggestions/{userid}")
    Call<List <Suggestion>> fetchSuggestions(@Path("userid") String userId);
}
