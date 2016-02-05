package com.randomappsinc.blanknavigationdrawer.API.Services;

import com.randomappsinc.blanknavigationdrawer.API.Models.IgnoredResponse;
import com.randomappsinc.blanknavigationdrawer.API.Models.Profile;
import com.randomappsinc.blanknavigationdrawer.API.Models.RequestBundle;
import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.API.Models.UserThumbnail;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public interface MatchingService {
    @GET("/suggestions/{userid}")
    Call<List <UserThumbnail>> fetchSuggestions(@Path("userid") String userId);

    @GET("/search/{id}/{gender}/{range}/{src}/{dest}")
    Call<List <UserThumbnail>> searchResults(@Path("id") String id, @Path("gender") String gender,
                                             @Path("range") String range, @Path("src") String src, @Path("dest") String dest);

    @GET("/profile/{req_id}/{target_id}")
    Call<Profile> getProfile(@Path("req_id") String req_id, @Path("target_id") String target_id);

    @POST("/connections/request")
    Call<IgnoredResponse> sendRequest(@Body RequestBundle requestBundle);

    @POST("/connections/accept")
    Call<IgnoredResponse> acceptRequest(@Body RequestBundle requestBundle);

    @POST("/connections/reject")
    Call<IgnoredResponse> rejectRequest(@Body RequestBundle requestBundle);
}
