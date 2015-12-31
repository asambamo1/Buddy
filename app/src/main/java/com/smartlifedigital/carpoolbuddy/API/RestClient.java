package com.smartlifedigital.carpoolbuddy.API;

import com.smartlifedigital.carpoolbuddy.API.Services.MatchingService;
import com.smartlifedigital.carpoolbuddy.API.Services.UserService;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by alexanderchiou on 12/25/15.
 */
public class RestClient {
    private static RestClient instance;
    private UserService userService;
    private MatchingService matchingService;

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
        matchingService = retrofit.create(MatchingService.class);
    }

    public UserService getUserService() {
        return userService;
    }

    public MatchingService getMatchingService() {
        return matchingService;
    }
}
