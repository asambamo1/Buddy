package com.randomappsinc.blanknavigationdrawer.API.Services;

import com.randomappsinc.blanknavigationdrawer.API.Models.IgnoredResponse;
import com.randomappsinc.blanknavigationdrawer.API.Models.LoginBundle;
import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.API.Models.UserId;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

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
}
