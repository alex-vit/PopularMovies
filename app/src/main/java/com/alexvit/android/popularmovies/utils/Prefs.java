package com.alexvit.android.popularmovies.utils;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.alexvit.android.popularmovies.R;

import java.util.Arrays;

/**
 * Created by Aleksandrs Vitjukovs on 7/16/2017.
 */

public class Prefs {

    private Prefs() {
    }

    public static String getCategory(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(
                context.getString(R.string.pref_category_key),
                Constants.CATEGORY_POPULAR
        );
    }

    public static CharSequence getCategoryTitle(Context context, String sortBy) {
        CharSequence[] values = context.getResources().getStringArray(R.array.pref_category_values);
        CharSequence[] titles = context.getResources().getStringArray(R.array.pref_category_entries);
        int idx = Arrays.asList(values).indexOf(sortBy);
        return titles[idx];
    }

}
