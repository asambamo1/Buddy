package com.randomappsinc.blanknavigationdrawer.API.Callbacks;

import android.content.Context;

import com.randomappsinc.blanknavigationdrawer.API.ApiConstants;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.ChangeVisibilityEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.Events.StatusChangeEvent;
import com.randomappsinc.blanknavigationdrawer.API.Models.IgnoredResponse;
import com.randomappsinc.blanknavigationdrawer.R;
import com.randomappsinc.blanknavigationdrawer.Utils.MyApplication;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by jman0_000 on 1/14/2016.
 */
public class ChangeVisibilityCallback implements Callback<IgnoredResponse> {
    private String success;

    public ChangeVisibilityCallback() {
        Context context = MyApplication.getAppContext();
        success = context.getString(R.string.success);
    }

    @Override
    public void onResponse(Response<IgnoredResponse> response, Retrofit retrofit) {
        switch (response.code()) {
            case ApiConstants.HTTP_STATUS_OK:
                EventBus.getDefault().post(new ChangeVisibilityEvent(success));
            default:
                EventBus.getDefault().post(new ChangeVisibilityEvent(null));
        }
    }

    @Override
    public void onFailure(Throwable t) { EventBus.getDefault().post(new StatusChangeEvent(null)); }
}
