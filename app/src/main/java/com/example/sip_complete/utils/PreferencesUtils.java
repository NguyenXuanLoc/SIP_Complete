package com.example.sip_complete.utils;

import android.content.Context;

import com.example.sip_complete.model.AccountInfoModel;

public class PreferencesUtils {
    public static String PREF_ACCOUNT_INFO = "PREF_ACCOUNT_INFO";

    public static void saveAccountInfo(AccountInfoModel model, Context context) {
        PreferencesHelper.getInstance(context).putObject(PREF_ACCOUNT_INFO, model);
    }

    public static AccountInfoModel getAccountInfo(Context context) {
        AccountInfoModel model = PreferencesHelper.getInstance(context).getAccountInfo(PREF_ACCOUNT_INFO);
        return model;
    }
}
