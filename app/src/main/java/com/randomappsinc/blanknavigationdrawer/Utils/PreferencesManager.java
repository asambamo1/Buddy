package com.randomappsinc.blanknavigationdrawer.Utils;

/**
 * Created by Aravind on 12/20/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.randomappsinc.blanknavigationdrawer.API.Models.User;

/**
 * Created by alexanderchiou on 7/14/15.
 */
public class PreferencesManager {
    private SharedPreferences prefs;

    private static final String USER_ID_KEY = "userId";
    private static final String VILLAGE_KEY = "village";
    private static final String WORK_ZIP_KEY = "workZip";
    private static final String NAME_KEY = "name";
    private static final String ABOUT_ME_KEY = "aboutMe";
    private static final String EMAIL_KEY = "email";
    private static final String PHONE_NUMBER_KEY = "phoneNumber";
    private static final String NONE_ADDED = "None added.";

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
    }

    public void setProfile(User user) {
        prefs.edit().putLong(USER_ID_KEY, user.getUserId()).apply();
        prefs.edit().putString(VILLAGE_KEY, user.getVillage()).apply();
        prefs.edit().putInt(WORK_ZIP_KEY, user.getZipCode()).apply();
        prefs.edit().putString(NAME_KEY, user.getName()).apply();
        prefs.edit().putString(ABOUT_ME_KEY, user.getAboutMe()).apply();
        prefs.edit().putString(EMAIL_KEY, user.getEmail()).apply();
        prefs.edit().putString(PHONE_NUMBER_KEY, user.getPhoneNumber()).apply();
    }

    public User getProfile() {
        User user = new User();

        user.setUserId(prefs.getLong(USER_ID_KEY, -1));
        user.setVillage(prefs.getString(VILLAGE_KEY, ""));
        user.setZipCode(prefs.getInt(WORK_ZIP_KEY, 0));
        user.setName(prefs.getString(NAME_KEY, ""));
        user.setAboutMe(prefs.getString(ABOUT_ME_KEY, ""));
        user.setEmail(prefs.getString(EMAIL_KEY, NONE_ADDED));
        user.setPhoneNumber(prefs.getString(PHONE_NUMBER_KEY, NONE_ADDED));

        return user;
    }
}