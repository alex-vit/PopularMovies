package com.example.android.popularmovies.util;

import android.content.Context;
import android.support.v7.preference.PreferenceManager;

import com.example.android.popularmovies.R;

import java.util.Arrays;

/**
 * Created by Aleksandrs Vitjukovs on 7/16/2017.
 */

public class Prefs {

    public static String getSortBy(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(
                context.getString(R.string.pref_sort_order_key),
                Api.SortBy.popularityDesc
        );
    }

    public static CharSequence getSortByTitle(Context context, String sortBy) {
        CharSequence[] values = context.getResources().getStringArray(R.array.pref_sort_by_values);
        CharSequence[] titles = context.getResources().getStringArray(R.array.pref_sort_by_entries);
        int idx = Arrays.asList(values).indexOf(sortBy);
        return titles[idx];
    }

}