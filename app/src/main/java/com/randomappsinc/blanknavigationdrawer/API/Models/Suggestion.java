package com.randomappsinc.blanknavigationdrawer.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public class Suggestion {
    @SerializedName("user_id")
    @Expose
    private long userId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("village")
    @Expose
    private String village;

    @SerializedName("zip_code")
    @Expose
    private long zip_code;

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getVillage() {
        return village;
    }

    public long getZip_code() {
        return zip_code;
    }
}
