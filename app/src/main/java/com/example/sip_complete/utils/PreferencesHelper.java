package com.example.sip_complete.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.example.sip_complete.model.AccountInfoModel;
import com.google.gson.Gson;

public class PreferencesHelper {
    private static final String PREF_KEY = "SIP_TEST";
    private static final String PREF_USER_NAME = "USER_NAME";
    private static final String PREF_DOMAIN = "DOMAIN";
    private static final String PREF_PASS = "PASS";
    private static final String PREF_ACC_ID = "ACC_ID";
    private static final String PREF_REGISTRAR = "ACC_ID";


    private SharedPreferences sharedPreference;
    public SharedPreferences.Editor editor;
    public static PreferencesHelper mPrefs;

    public PreferencesHelper(Context context) {
        sharedPreference = context.getSharedPreferences(PREF_KEY, 0);
        editor = sharedPreference.edit();
    }

    public static PreferencesHelper getInstance(Context context) {
        if (mPrefs == null)
            mPrefs = new PreferencesHelper(context.getApplicationContext());
        return mPrefs;
    }

    public static void dispose() {
        mPrefs = null;
    }

    public boolean getBoolean(String key, boolean default_value) {
        return sharedPreference.getBoolean(key, default_value);
    }

    public float getFloat(String key, float default_value) {
        return sharedPreference.getFloat(key, default_value);
    }

    public int getInt(String key, int default_value) {
        return sharedPreference.getInt(key, default_value);
    }

    public long getLong(String key, long default_value) {
        return sharedPreference.getLong(key, default_value);
    }

    public String getString(String key, String default_value) {
        return sharedPreference.getString(key, default_value);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        commitOrApplyPreferences(editor);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        commitOrApplyPreferences(editor);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        commitOrApplyPreferences(editor);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        commitOrApplyPreferences(editor);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        commitOrApplyPreferences(editor);
    }

    public void putObject(String key, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(key, json);
        commitOrApplyPreferences(editor);
    }

    public  AccountInfoModel getAccountInfo(String key) {
        Gson gson = new Gson();
        String json = mPrefs.getString(key, "");
        AccountInfoModel model = gson.fromJson(json, AccountInfoModel.class);
        return model;
    }

    private void commitOrApplyPreferences(SharedPreferences.Editor preferencesEditor) {
        try {
            if (Build.VERSION.SDK_INT >= 9)
                preferencesEditor.apply();
            else
                preferencesEditor.commit();
        } catch (Throwable t) {
            if (t instanceof OutOfMemoryError) {
                System.gc();
                if (Build.VERSION.SDK_INT >= 9)
                    preferencesEditor.commit();
            }
        }
    }
}

