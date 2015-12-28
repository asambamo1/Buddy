package com.randomappsinc.blanknavigationdrawer.API;

import com.randomappsinc.blanknavigationdrawer.API.Services.MatchingService;
import com.randomappsinc.blanknavigationdrawer.API.Services.UserService;

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
