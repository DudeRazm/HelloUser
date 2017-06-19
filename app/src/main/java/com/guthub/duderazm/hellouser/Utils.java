package com.guthub.duderazm.hellouser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author DudeRazm on 18.06.2017.
 */

public class Utils {
    public static void hideKeyBoard(@NonNull View view, Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
