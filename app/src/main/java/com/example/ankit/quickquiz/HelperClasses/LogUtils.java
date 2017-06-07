package com.example.ankit.quickquiz.HelperClasses;

import android.content.Context;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Ankit on 4/1/2017.
 */

public class LogUtils {

    public static final int MY_SOCKET_TIMEOUT_MS = 10000;

    public static final String LOG_TAG = "Log message";

    public static void T(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public static void showSnackBar(Context context, View view, String message) {
        Snackbar.make(view, message, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    public static void L(String message) {
        Log.d(LOG_TAG, message);
    }

}
