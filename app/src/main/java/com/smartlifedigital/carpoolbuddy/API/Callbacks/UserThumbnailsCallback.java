package com.smartlifedigital.carpoolbuddy.API.Callbacks;

import com.smartlifedigital.carpoolbuddy.API.ApiConstants;
import com.smartlifedigital.carpoolbuddy.API.Models.Events.UserThumbnailsEvent;
import com.smartlifedigital.carpoolbuddy.API.Models.UserThumbnail;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public class UserThumbnailsCallback implements Callback<List<UserThumbnail>> {
    private String screen;

    public UserThumbnailsCallback(String screen) {
        this.screen = screen;
    }

    @Override
    public void onResponse(Response<List<UserThumbnail>> response, Retrofit retrofit) {
        switch (response.code()) {
            case ApiConstants.HTTP_STATUS_OK:
                EventBus.getDefault().post(new UserThumbnailsEvent(screen, response.body()));
                break;
            default:
                EventBus.getDefault().post(new UserThumbnailsEvent(screen, null));
                break;
        }
    }

    @Override
    public void onFailure(Throwable t) {
        EventBus.getDefault().post(new UserThumbnailsEvent(screen, null));
    }
}
