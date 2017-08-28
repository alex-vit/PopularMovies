package com.alexvit.android.popularmovies.utils;

import android.content.Context;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

public class Toast {
    private Toast() {
    }

    public static void toast(Context context, String message) {
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
