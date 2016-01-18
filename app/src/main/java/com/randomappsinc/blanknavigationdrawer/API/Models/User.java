package com.randomappsinc.blanknavigationdrawer.API.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexanderchiou on 12/25/15.
 * Used for create account
 */
public class User implements Parcelable {
    @SerializedName("user_id")
    @Expose
    private long userId;

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

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("visible")
    @Expose
    private boolean visible;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public int getHomeZip() {
        return homeZip;
    }

    public void setHomeZip(int homeZip) {
        this.homeZip = homeZip;
    }

    public int getWorkZip() {
        return workZip;
    }

    public void setWorkZip(int workZip) {
        this.workZip = workZip;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getVisible() { return visible; }

    public void setVisible(boolean visible) { this.visible = visible; }

    public User() {}

    protected User(Parcel in) {
        userId = in.readLong();
        name = in.readString();
        aboutMe = in.readString();
        gender = in.readString();
        homeZip = in.readInt();
        workZip = in.readInt();
        phoneNumber = in.readString();
        email = in.readString();
        password = in.readString();
        visible = (boolean) in.readValue(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(userId);
        dest.writeString(name);
        dest.writeString(aboutMe);
        dest.writeString(gender);
        dest.writeInt(homeZip);
        dest.writeInt(workZip);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeValue(visible);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
