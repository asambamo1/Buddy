package com.randomappsinc.blanknavigationdrawer.API.Callbacks;

import com.randomappsinc.blanknavigationdrawer.API.ApiConstants;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.ProfileEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.Profile;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public class ProfileCallback implements Callback<Profile> {
    @Override
    public void onResponse(Response<Profile> response, Retrofit retrofit) {
        switch (response.code()) {
            case ApiConstants.HTTP_STATUS_OK:
                EventBus.getDefault().post(new ProfileEvent(response.body()));
                break;
            default:
                EventBus.getDefault().post(new ProfileEvent(null));
                break;
        }
    }

    @Override
    public void onFailure(Throwable t) {
        EventBus.getDefault().post(new ProfileEvent(null));
    }


}
