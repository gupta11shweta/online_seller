package com.test.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.test.myapplication.MyApplication;

final public class SharedPrefsUtils {
    private SharedPreferences mSharedPreferences;

    private SharedPrefsUtils() {
        init();
    }

    public static synchronized SharedPrefsUtils getInstance() {
        return new SharedPrefsUtils();
    }

    public static synchronized SharedPreferences getPreferenceInstance() {
        return new SharedPrefsUtils().mSharedPreferences;
    }

    private void init() {
        mSharedPreferences = MyApplication.getApplicationCtx().getSharedPreferences("pe", Context.MODE_PRIVATE);
    }

    public static String getStringPreference(String key, String defaultValue) {
        String value = null;
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null) {
            value = preferences.getString(key, defaultValue);
        }
        return value;
    }

    public static boolean setStringPreference(String key, String value) {
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null && StringUtils.isNotBlank(key)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            return editor.commit();
        }
        return false;
    }

    public static float getFloatPreference(String key, float defaultValue) {
        float value = defaultValue;
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null) {
            value = preferences.getFloat(key, defaultValue);
        }
        return value;
    }

    public static boolean setFloatPreference(String key, float value) {
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat(key, value);
            return editor.commit();
        }
        return false;
    }

    public static long getLongPreference(String key, long defaultValue) {
        long value = defaultValue;
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null) {
            value = preferences.getLong(key, defaultValue);
        }
        return value;
    }

    public static boolean setLongPreference(String key, long value) {
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong(key, value);
            return editor.commit();
        }
        return false;
    }

    public static int getIntegerPreference(String key, int defaultValue) {
        int value = defaultValue;
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null) {
            value = preferences.getInt(key, defaultValue);
        }
        return value;
    }

    public static boolean setIntegerPreference(String key, int value) {
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            return editor.commit();
        }
        return false;
    }

    public static boolean getBooleanPreference(String key, boolean defaultValue) {
        boolean value = defaultValue;
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null) {
            value = preferences.getBoolean(key, defaultValue);
        }
        return value;
    }

    public static boolean setBooleanPreference(String key, boolean value) {
        SharedPreferences preferences = getPreferenceInstance();
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(key, value);
            return editor.commit();
        }
        return false;
    }

    public static void clear() {
        SharedPreferences preferences = getPreferenceInstance();
        preferences.edit().clear().apply();
    }
}
