package com.randomappsinc.blanknavigationdrawer.API.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jman0_000 on 12/27/2015.
 */
public class Profile {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("about_me")
    @Expose
    private String about_me;

    @SerializedName("village")
    @Expose
    private String village;

    @SerializedName("phone_number")
    @Expose
    private String phone_number;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("zip_code")
    @Expose
    private int zipCode;

    @SerializedName("status")
    @Expose
    private String status;

    public String getName() {
        return name;
    }

    public String getAbout_me() {
        return about_me;
    }

    public String getVillage() {
        return village;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getStatus() {
        return status;
    }
}
