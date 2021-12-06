package com.example.phonepeoffers.Account_new;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;

public class storeuserdata {
    private static final String LIST_KEY = "list_key";
    private static final String Userkey = "userkey";
    private static final String Members_KEY = "memberskey";

    public static void saveuserdata(Context context, JSONArray userdata) {
        String s = String.valueOf(userdata);
//        Log.d("userdat",s);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Userkey, s);
        editor.apply();

    }

    public static String getuserdata(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonstring = pref.getString(Userkey, "");
        return jsonstring;
    }
}