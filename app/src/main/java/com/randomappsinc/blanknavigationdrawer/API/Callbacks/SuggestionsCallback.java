package com.randomappsinc.blanknavigationdrawer.API.Callbacks;

import com.randomappsinc.blanknavigationdrawer.API.ApiConstants;
import com.randomappsinc.blanknavigationdrawer.API.Models.Suggestion;
import com.randomappsinc.blanknavigationdrawer.API.Models.SuggestionsEvent;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public class SuggestionsCallback implements Callback<List<Suggestion>> {
    @Override
    public void onResponse(Response<List<Suggestion>> response, Retrofit retrofit) {
        switch (response.code()) {
            case ApiConstants.HTTP_STATUS_OK:
                EventBus.getDefault().post(new SuggestionsEvent(null));
                break;
            default:
                EventBus.getDefault().post(new SuggestionsEvent(null));
                break;
        }
    }

    @Override
    public void onFailure(Throwable t) {
        EventBus.getDefault().post(new SuggestionsEvent(null));
    }
}
