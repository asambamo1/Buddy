package com.randomappsinc.blanknavigationdrawer.Utils;

/**
 * Created by Aravind on 12/20/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.randomappsinc.blanknavigationdrawer.API.Models.User;
import com.randomappsinc.blanknavigationdrawer.R;

/**
 * Created by alexanderchiou on 7/14/15.
 */
public class PreferencesManager {
    private SharedPreferences prefs;
    private String NONE_ADDED;

    private static PreferencesManager instance;

    public static PreferencesManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    private static synchronized PreferencesManager getSync() {
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    private PreferencesManager() {
        Context context = MyApplication.getAppContext().getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        NONE_ADDED = context.getString(R.string.none_added);
    }

    public void setProfile(User user) {
        prefs.edit().putLong(Constants.USER_ID_KEY, user.getUserId()).apply();
        prefs.edit().putInt(Constants.HOME_ZIP_KEY, user.getHomeZip()).apply();
        prefs.edit().putInt(Constants.WORK_ZIP_KEY, user.getWorkZip()).apply();
        prefs.edit().putString(Constants.NAME_KEY, user.getName()).apply();
        prefs.edit().putString(Constants.GENDER_KEY, user.getGender()).apply();
        prefs.edit().putString(Constants.ABOUT_ME_KEY, user.getAboutMe()).apply();
        prefs.edit().putString(Constants.EMAIL_KEY, user.getEmail()).apply();
        prefs.edit().putString(Constants.PHONE_NUMBER_KEY, user.getPhoneNumber()).apply();
    }

    public User getProfile() {
        User user = new User();

        user.setUserId(prefs.getLong(Constants.USER_ID_KEY, -1));
        user.setHomeZip(prefs.getInt(Constants.HOME_ZIP_KEY, 0));
        user.setWorkZip(prefs.getInt(Constants.WORK_ZIP_KEY, 0));
        user.setName(prefs.getString(Constants.NAME_KEY, ""));
        user.setGender(prefs.getString(Constants.GENDER_KEY, ""));
        user.setAboutMe(prefs.getString(Constants.ABOUT_ME_KEY, ""));
        user.setEmail(prefs.getString(Constants.EMAIL_KEY, NONE_ADDED));
        user.setPhoneNumber(prefs.getString(Constants.PHONE_NUMBER_KEY, NONE_ADDED));

        return user;
    }

    public void logout() {
        prefs.edit().clear().apply();
    }
}