package com.randomappsinc.blanknavigationdrawer.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jman0_000 on 12/27/2015.
 * Used for connection requests, already existing connections, and suggestions
 */
public class UserThumbnail {
    @SerializedName("user_id")
    @Expose
    private long userId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("src_zip")
    @Expose
    private int homeZip;

    @SerializedName("dest_zip")
    @Expose
    private int workZip;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("status")
    @Expose
    private String status;

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getWorkZip() {
        return workZip;
    }

    public int getHomeZip() {
        return homeZip;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }
}
