package com.randomappsinc.blanknavigationdrawer.Utils;

/**
 * Created by Aravind on 12/20/2015.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by alexanderchiou on 7/14/15.
 */
public class PreferencesManager {
    private SharedPreferences prefs;


    private static final String USER_ID_KEY = "User ID";

    private static PreferencesManager instance;

    public static PreferencesManager get() {
        if (instance == null) {
            instance = getSync();
        }
        return instance;
    }

    public void setId(int id){
        prefs.edit().putInt(USER_ID_KEY, id).apply();
    }

    public int getId(){
        return prefs.getInt(USER_ID_KEY, -1);
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

}