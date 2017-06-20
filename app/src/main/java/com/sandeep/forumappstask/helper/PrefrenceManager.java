package com.sandeep.forumappstask.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sandeep on 16-06-2017.
 */

public class PrefrenceManager {
    public static final String SHARED_PREF_NAME = "FCMSharedPref";
    public static final String USER_ID = "user_id";
    public static final String TAG_TOKEN = "tagtoken";
    public static final String TAG_EMAIL = "tagemail";
    public static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static PrefrenceManager mInstance;
    private static Context mCtx;


    private PrefrenceManager(Context context) {
        mCtx = context;
    }

    public static synchronized PrefrenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefrenceManager(context);
        }
        return mInstance;
    }

    public boolean saveDeviceToken(String token) {
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.commit();
        return true;
    }

    public String getDeviceToken() {
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TAG_TOKEN, Constants.STR_DEFAULT);
    }

    public boolean saveEmail(String email) {
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(TAG_EMAIL, email);
        editor.commit();
        return true;
    }

    public String getEmail() {
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(TAG_EMAIL, Constants.STR_DEFAULT);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void saveUniqueId(String unique_id) {
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Constants.UNIQUE_ID, unique_id);
        editor.commit();
    }

    public String getUniqueId() {
        sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.UNIQUE_ID, Constants.STR_DEFAULT);
    }
}
