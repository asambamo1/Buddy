package com.randomappsinc.blanknavigationdrawer.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexanderchiou on 12/25/15.
 */
public class UserId {
    @SerializedName("id")
    @Expose
    private long userId;

    public long getUserId() {
        return userId;
    }
}
