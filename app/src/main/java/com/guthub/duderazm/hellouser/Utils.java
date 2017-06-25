package com.guthub.duderazm.hellouser;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author DudeRazm on 18.06.2017.
 */

public class Utils {

    public static final String IS_LOGGED_IN = "Logged_in";

    public static void hideKeyBoard(@NonNull View view, Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void writeIsAlreadyLoggedIn(Activity activity, boolean loggedIn){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IS_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public static boolean readIsAlreadyLoggedIn(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        boolean defaultValue = false;
        return sharedPref.getBoolean(IS_LOGGED_IN, defaultValue);

    }
}
