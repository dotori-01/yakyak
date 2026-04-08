package com.app.smh;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {

    private static final String PREF_NAME = "smh_settings";
    private static final String KEY_HOME_MESSAGE = "key_home_message";
    private static final String DEFAULT_HOME_MESSAGE = "오늘의 건강이 내일을 지킨다";

    public static void saveHomeMessage(Context context, String message) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_HOME_MESSAGE, message).apply();
    }

    public static String getHomeMessage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_HOME_MESSAGE, DEFAULT_HOME_MESSAGE);
    }
}