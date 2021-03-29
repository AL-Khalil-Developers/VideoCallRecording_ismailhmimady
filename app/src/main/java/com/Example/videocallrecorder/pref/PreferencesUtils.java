package com.Example.videocallrecorder.pref;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import com.Example.videocallrecorder.R;

public class PreferencesUtils {
    public static final String DEVICE_TOKEN = "device_token";
    public static final String EXIT_JSON = "exit_json";
    public static String PREF_NAME = null;
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String SPLASH_1_JSON = "splash1_json";
    public static final String TIME_OF_GET_APP_EXIT = "time_of_get_app_EXIT";
    public static final String TIME_OF_GET_APP_SPLASH = "time_of_get_app_splash";
    private static Context mContext;
    private static PreferencesUtils preferencesUtils;

    public static PreferencesUtils getInstance(Context context) {
        mContext = context;
        PREF_NAME = context.getString(R.string.app_name);
        if (preferencesUtils == null) {
            preferencesUtils = new PreferencesUtils();
        }
        return preferencesUtils;
    }

    public String getPrefString(String str) {
        return mContext != null ? mContext.getSharedPreferences(PREF_NAME, 0).getString(str, "") : "";
    }

    public void setPrefString(String str, String str2) {
        Editor edit = mContext.getSharedPreferences(PREF_NAME, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }
}
