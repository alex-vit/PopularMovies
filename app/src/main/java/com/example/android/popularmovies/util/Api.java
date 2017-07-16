package com.example.android.popularmovies.util;

import android.database.Cursor;
import android.net.Uri;

import com.example.android.popularmovies.data.MovieContract.MovieEntry;
import com.example.android.popularmovies.models.Movie;

/**
 * Created by Aleksandrs Vitjukovs on 6/2/2017.
 */

public class Api {

    //    private static final String TEST_POSTER_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    public static final String API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String TAG = Api.class.getSimpleName();
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";

    public static String fullImageUrl(String imagePath) {
        return fullImageUrl(imagePath, PosterSize.w185);
    }

    public static String fullImageUrl(String imagePath, String size) {
        return Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(size)
                .appendPath(imagePath.substring(1))
                .build()
                .toString();
    }

    public static Movie movieFromCursor(Cursor cursor) {
        Movie m = new Movie();
        m.id = cursor.getInt(cursor.getColumnIndex(MovieEntry._ID));
        m.title = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE));
        m.releaseDate = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE));
        m.overview = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW));
        m.posterPath = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH));
        m.backdropPath = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_BACKDROP_PATH));
        m.voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE));
        m.voteCount = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_COUNT));
        m.isFavorite = (
                cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_IS_FAVORITE)) == 1
        );
        return m;
    }

    public static String defaultSorting() {
        return SortBy.popularityDesc;
    }

    public static final class Param {
        public static final String apiKey = "api_key";
        public static final String sortBy = "sort_by";
        public static final String voteCount = "vote_count.gte";
    }

    public static final class SortBy {
        public static final String popularityDesc = "popularity.desc";
        public static final String voteAverageDesc = "vote_average.desc";
        public static final String favorite = "favorite";
    }

    public static final class PosterSize {
        public static final String w185 = "w185";
        public static final String w342 = "w342";
    }

    public static final class BackdropSize {
        public static final String w300 = "w300";
        public static final String w780 = "w780";
    }
}