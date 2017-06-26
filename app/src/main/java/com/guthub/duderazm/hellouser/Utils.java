package com.guthub.duderazm.hellouser;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.jasypt.util.password.BasicPasswordEncryptor;

import java.sql.SQLOutput;

/**
 * @author DudeRazm on 18.06.2017.
 */

public class Utils {

    public static final String IS_LOGGED_IN = "Logged_in";
    private static final String USER_LOGGIN = "user login";
    private static final String USER_PASSWORD = "user_password";

    private static BasicPasswordEncryptor mEncrptor;

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

    public static void writeUserLogin(Activity activity, @NonNull String userLogin){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_LOGGIN, userLogin);
        editor.apply();
    }

    public static String readUserLogin(Activity activity){
        SharedPreferences sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
        String defaultValue = " world";
        return "" + sharedPreferences.getString(USER_LOGGIN, defaultValue);
    }

    public static void writePassword(Activity activity, @NonNull String password) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_PASSWORD, makeEncryptedPassword(password));
        editor.apply();
    }
    public static boolean isPasswordMatching (Activity activity, @NonNull String newPassword) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String defValue = "rt8sj0d23nMq#2";
        String savedPasswordEncrypted = sharedPreferences.getString(USER_PASSWORD, defValue);
        return getEncyptor().checkPassword(newPassword, savedPasswordEncrypted);
    }

    private static String makeEncryptedPassword(String password) {
        return getEncyptor().encryptPassword(password);
    }

    private static BasicPasswordEncryptor getEncyptor() {
        if(mEncrptor == null) {
            mEncrptor = new BasicPasswordEncryptor();
        }
        return  mEncrptor;
        }

}
