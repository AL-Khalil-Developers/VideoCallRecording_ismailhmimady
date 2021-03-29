package com.Example.videocallrecorder.Main.Global;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.net.NetworkInfo;
import com.Example.videocallrecorder.R;

public class Globals {
    private static Editor context1;
    private static NetworkInfo context12;

    public static boolean getPrebuttonfBoolean(Context context, String str) {
        return context != null ? context.getSharedPreferences(context.getString(R.string.app_name), 0).getBoolean(str, false) : false;
    }

    public static void setPrefbuttonBoolean(Context context, String str, boolean z) {
        context1 = context.getSharedPreferences(context.getString(R.string.app_name), 0).edit();
        context1.putBoolean(str, z);
        context1.apply();
    }
}
