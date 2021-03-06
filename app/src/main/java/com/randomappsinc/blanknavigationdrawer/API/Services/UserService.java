package com.randomappsinc.blanknavigationdrawer.API.Services;

import com.randomappsinc.blanknavigationdrawer.API.Models.IgnoredResponse;
import com.randomappsinc.blanknavigationdrawer.API.Models.LoginBundle;
import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.API.Models.UserId;
import com.randomappsinc.blanknavigationdrawer.API.Models.UserThumbnail;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by alexanderchiou on 12/25/15.
 */
public interface UserService {
    @POST("/createAccount")
    Call<UserId> createAccount(@Body User user);

    @POST("/login")
    Call<User> login(@Body LoginBundle loginBundle);

    @POST("/updateProfile")
    Call<IgnoredResponse> updateProfile(@Body User user);

    @GET("connections/{userId}")
    Call< List<UserThumbnail>> fetchConnections(@Path("userId") String userId);

    @GET("/connections/{mode}/{userId}")
    Call< List<UserThumbnail>> fetchRequests(@Path("mode") String mode, @Path("userId") String userId);

    @GET("changevisibility/{state}/{userId}")
    Call<IgnoredResponse> changeVisibility(@Path("state") String visibility, @Path("userId") String userId);
}
