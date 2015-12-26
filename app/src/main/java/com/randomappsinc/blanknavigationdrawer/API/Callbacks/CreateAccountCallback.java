package com.randomappsinc.blanknavigationdrawer.API.Callbacks;

import android.content.Context;

import com.randomappsinc.blanknavigationdrawer.API.ApiConstants;
import com.randomappsinc.blanknavigationdrawer.API.Models.SnackbarEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.API.Models.UserId;
import com.randomappsinc.blanknavigationdrawer.Activities.Onboarding.PasswordActivity;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.MyApplication;
import com.randomappsinc.blanknavigationdrawer.Utils.PreferencesManager;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by alexanderchiou on 12/25/15.
 */
public class CreateAccountCallback implements Callback<UserId> {
    public static final String CREATE_ACCOUNT_SUCCESS = "createAccountSuccess";

    private User user;
    private String badRequestMessage;
    private String internalServerErrorMessage;

    public CreateAccountCallback(User user) {
        Context context = MyApplication.getAppContext();
        this.user = user;
        this.badRequestMessage = context.getString(R.string.email_already_used);
        this.internalServerErrorMessage = context.getString(R.string.create_account_failed);
    }

    @Override
    public void onResponse(Response<UserId> response, Retrofit retrofit) {
        switch (response.code()) {
            case ApiConstants.HTTP_STATUS_OK:
                user.setUserId(response.body().getUserId());
                PreferencesManager.get().setProfile(user);
                EventBus.getDefault().post(CREATE_ACCOUNT_SUCCESS);
                break;
            case ApiConstants.HTTP_BAD_REQUEST:
                EventBus.getDefault().post(new SnackbarEvent(PasswordActivity.SCREEN_TAG, badRequestMessage));
                break;
            case ApiConstants.HTTP_INTERNAL_SERVER_ERROR:
                EventBus.getDefault().post(new SnackbarEvent(PasswordActivity.SCREEN_TAG, internalServerErrorMessage));
        }
    }

    @Override
    public void onFailure(Throwable t) {
        EventBus.getDefault().post(new SnackbarEvent(PasswordActivity.SCREEN_TAG, internalServerErrorMessage));
    }
}
