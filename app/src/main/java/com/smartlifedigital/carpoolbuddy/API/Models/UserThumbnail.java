package com.smartlifedigital.carpoolbuddy.API.Models;

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

    @SerializedName("village")
    @Expose
    private String village;

    @SerializedName("zip_code")
    @Expose
    private int zipCode;

    @SerializedName("status")
    @Expose
    private String status;

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getVillage() {
        return village;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getStatus() {
        return status;
    }
}
