package com.alexvit.android.popularmovies.data;

import android.content.Context;
import android.support.v4.content.CursorLoader;

/**
 * Created by Aleksandrs Vitjukovs on 7/16/2017.
 */

public class MovieSqlLoader extends CursorLoader {

    public MovieSqlLoader(Context context) {
        super(context);
        setUri(MovieContract.MovieEntry.CONTENT_URI);
        setSelection("isFavorite = ?");
        setSelectionArgs(new String[]{"1"});
    }

}
