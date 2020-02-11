package com.example.truthordare.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.example.truthordare.R;

public class PreferenceUtils {
    private static final String PREFERENCE_KEY = "TRT_TRUTH_DARE_PREF";
    public static String TAG = PreferenceUtils.class.getSimpleName();

    private static SharedPreferences getPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static Editor getEditor(Context context) {
        return getPreference(context).edit();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key, String defaultVal) {
        return getPreference(context).getString(key, defaultVal);
    }

    public static Float getFloat(Context context, String key) {
        return getFloat(context, key, Float.valueOf(0.0f));
    }

    public static Float getFloat(Context context, String key, Float defaultVal) {
        return Float.valueOf(getPreference(context).getFloat(key, defaultVal.floatValue()));
    }

    public static Boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, Boolean.FALSE);
    }

    public static Boolean getBoolean(Context context, String key, Boolean defaultVal) {
        return Boolean.valueOf(getPreference(context).getBoolean(key, defaultVal.booleanValue()));
    }

    public static Integer getInteger(Context context, String key) {
        return getInteger(context, key, Integer.valueOf(0));
    }

    public static Integer getInteger(Context context, String key, Integer defaultVal) {
        return Integer.valueOf(getPreference(context).getInt(key, defaultVal.intValue()));
    }

    public static Long getLong(Context context, String key) {
        return getLong(context, key, Long.valueOf(0));
    }

    public static Long getLong(Context context, String key, Long defaultVal) {
        return Long.valueOf(getPreference(context).getLong(key, defaultVal.longValue()));
    }

    public static void setString(Context context, String key, String val) {
        Editor editor = getEditor(context);
        editor.putString(key, val);
        editor.commit();
    }

    public static void setBoolean(Context context, String key, Boolean val) {
        Editor editor = getEditor(context);
        editor.putBoolean(key, val.booleanValue());
        editor.commit();
    }

    public static void setFloat(Context context, String key, Float val) {
        Editor editor = getEditor(context);
        editor.putFloat(key, val.floatValue());
        editor.commit();
    }

    public static void setLong(Context context, String key, Long val) {
        Editor editor = getEditor(context);
        editor.putLong(key, val.longValue());
        editor.commit();
    }

    public static void setInt(Context context, String key, Integer val) {
        Editor editor = getEditor(context);
        editor.putInt(key, val.intValue());
        editor.commit();
    }

    public static Boolean isSoundOn(Context context) {
        return getBoolean(context, context.getResources().getString(R.string.KEY_SOUND), Boolean.TRUE);
    }

    public static Boolean isAutoNext(Context context) {
        return getBoolean(context, context.getResources().getString(R.string.KEY_AUTO_NEXT), Boolean.FALSE);
    }

    public static Boolean shouldShowPopup(Context context) {
        return Boolean.valueOf(!getBoolean(context, context.getResources().getString(R.string.KEY_DONT_SHOW_POPUP_ON_PLAY), Boolean.FALSE).booleanValue());
    }

    public static boolean toggleSound(Context context) {
        boolean isSoundOn = !isSoundOn(context).booleanValue();
        setBoolean(context, context.getResources().getString(R.string.KEY_SOUND), Boolean.valueOf(isSoundOn));
        return isSoundOn;
    }
}
