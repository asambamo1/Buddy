package com.smartlifedigital.carpoolbuddy.API.Callbacks;

import android.content.Context;

import com.smartlifedigital.carpoolbuddy.API.ApiConstants;
import com.smartlifedigital.carpoolbuddy.API.Models.Events.SnackbarEvent;
import com.smartlifedigital.carpoolbuddy.API.Models.User;
import com.smartlifedigital.carpoolbuddy.Activities.Onboarding.LoginActivity;
import com.smartlifedigital.carpoolbuddy.R;
import com.smartlifedigital.carpoolbuddy.Utils.MyApplication;
import com.smartlifedigital.carpoolbuddy.Utils.PreferencesManager;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by alexanderchiou on 12/25/15.
 */
public class LoginCallback implements Callback<User> {
    public static final String LOGIN_SUCCESS = "loginSuccess";

    private String unauthorizedMessage;
    private String internalServerErrorMessage;

    public LoginCallback() {
        Context context = MyApplication.getAppContext();
        this.unauthorizedMessage = context.getString(R.string.login_invalid);
        this.internalServerErrorMessage = context.getString(R.string.login_failed);
    }

    @Override
    public void onResponse(Response<User> response, Retrofit retrofit) {
        switch (response.code()) {
            case ApiConstants.HTTP_STATUS_OK:
                PreferencesManager.get().setProfile(response.body());
                EventBus.getDefault().post(LOGIN_SUCCESS);
                break;
            case ApiConstants.HTTP_UNAUTHORIZED:
                EventBus.getDefault().post(new SnackbarEvent(LoginActivity.SCREEN_TAG, unauthorizedMessage));
                break;
            case ApiConstants.HTTP_INTERNAL_SERVER_ERROR:
                EventBus.getDefault().post(new SnackbarEvent(LoginActivity.SCREEN_TAG, internalServerErrorMessage));
        }
    }

    @Override
    public void onFailure(Throwable t) {
        EventBus.getDefault().post(new SnackbarEvent(LoginActivity.SCREEN_TAG, internalServerErrorMessage));
    }
}

