package com.randomappsinc.blanknavigationdrawer.API.Callbacks;

import com.randomappsinc.blanknavigationdrawer.API.Models.UserId;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by alexanderchiou on 12/25/15.
 */
public class CreateAccountCallback implements Callback<UserId> {
    @Override
    public void onResponse(Response<UserId> response, Retrofit retrofit) {

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
