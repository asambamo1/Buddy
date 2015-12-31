package com.randomappsinc.blanknavigationdrawer.API.Callbacks;

import com.randomappsinc.blanknavigationdrawer.API.ApiConstants;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.StatusChangeEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.IgnoredResponse;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by alexanderchiou on 12/27/15.
 */
public class StatusChangeCallback implements Callback<IgnoredResponse> {
    private String newStatus;

    public StatusChangeCallback(String newStatus) {
        this.newStatus = newStatus;
    }

    @Override
    public void onResponse(Response<IgnoredResponse> response, Retrofit retrofit) {
        switch (response.code()) {
            case ApiConstants.HTTP_STATUS_OK:
                EventBus.getDefault().post(new StatusChangeEvent(newStatus));
                break;
            default:
                EventBus.getDefault().post(new StatusChangeEvent(null));
        }
    }

    @Override
    public void onFailure(Throwable t) {
        EventBus.getDefault().post(new StatusChangeEvent(null));
    }
}
