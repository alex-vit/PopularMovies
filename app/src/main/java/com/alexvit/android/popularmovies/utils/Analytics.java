package com.alexvit.android.popularmovies.utils;

import android.content.Context;
import android.os.Bundle;

import com.alexvit.android.popularmovies.data.Movie;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by alexander.vitjukov on 27.07.2017.
 */

public class Analytics {
    public static void logCategoryView(Context context, String category) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, category);
        getAnalytics(context).logEvent(FirebaseAnalytics.Event.VIEW_ITEM_LIST, bundle);
    }

    public static void logMovieView(Context context, Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, String.valueOf(movie.id));
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, movie.title);
        getAnalytics(context).logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }

    private static FirebaseAnalytics getAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }
}
