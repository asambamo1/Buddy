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
    private String aboutMe;

    @SerializedName("src_zip")
    @Expose
    private int homeZip;

    @SerializedName("dest_zip")
    @Expose
    private int workZip;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("gender")
    @Expose
    private String gender;

    public String getName() {
        return name;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public int getHomeZip() {
        return homeZip;
    }

    public int getWorkZip() {
        return workZip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getGender() {
        return gender;
    }
}
