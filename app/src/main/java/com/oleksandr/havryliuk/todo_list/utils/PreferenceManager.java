package com.oleksandr.havryliuk.todo_list.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Set;

public class PreferenceManager {

    private static final String TAG = PreferenceManager.class.getSimpleName();

    private static final String PREF_NAME = "testAppPreference";

    private static SharedPreferences sPref;
    private static SharedPreferences.Editor edit;

    public static void init(Context context) {
        sPref = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        edit = sPref.edit();
    }

    public static SharedPreferences getPrefs(Context cxt) {
        if (edit == null) {
            init(cxt);
        }
        return sPref;
    }

    public static void setStringSet(String key, Set<String> value) {
        Log.i(TAG, "setString " + key + "=" + value);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            edit.putStringSet(key, value);
            edit.commit();
        }
    }

    public static void setString(String key, String value) {
        Log.i(TAG, "setString " + key + "=" + value);
        edit.putString(key, value);
        edit.commit();
    }

    public static void setLong(String key, long value) {
        Log.i(TAG, "setLong " + key + "=" + value);
        edit.putLong(key, value);
        edit.commit();
    }

    public static void setBoolean(String key, boolean value) {
        Log.i(TAG, "setBoolean " + key + "=" + value);
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void setInt(String key, int value) {
        Log.i(TAG, "setInt " + key + "=" + value);
        edit.putInt(key, value);
        edit.commit();
    }

    public static void setPrefFloat(String key, float value) {
        Log.i(TAG, "setPrefFloat " + key + "=" + value);
        edit.putFloat(key, value);
        edit.commit();
    }


    // GETTERS
    public static Set<String> getAllKeys(){
        return sPref.getAll().keySet();
    }

    public static Set<String> getStringSet(String key) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            return sPref.getStringSet(key, null);
        }
        return null;
    }

    public static String getString(String key) {
        return sPref.getString(key, null);
    }

    public static String getString(String key, String def) {
        return sPref.getString(key, def);
    }

    public static boolean getBoolean(String key) {
        return sPref.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sPref.getBoolean(key, defValue);
    }

    public static long getLong(String key) {
        return sPref.getLong(key, -1);
    }

    public static int getInt(String key) {
        return sPref.getInt(key, -1);
    }

    public static int getInt(String key, int def) {
        return sPref.getInt(key, def);
    }

    public static float getFloat(String key) {
        return sPref.getFloat(key, -1);
    }

}
