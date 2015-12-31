package com.smartlifedigital.carpoolbuddy.API.Models;

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
    private String aboutMe;

    @SerializedName("village")
    @Expose
    private String village;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

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

    public String getAboutMe() {
        return aboutMe;
    }

    public String getVillage() {
        return village;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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
