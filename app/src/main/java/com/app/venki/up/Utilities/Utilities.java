package com.app.venki.up.Utilities;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by samsiu on 9/8/16.
 */
public class Utilities {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

}
