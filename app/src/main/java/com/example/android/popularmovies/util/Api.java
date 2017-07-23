package com.example.android.popularmovies.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.example.android.popularmovies.BuildConfig;
import com.example.android.popularmovies.models.Movie;

import java.util.Arrays;
import java.util.List;

import static com.example.android.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by Aleksandrs Vitjukovs on 6/2/2017.
 */

public class Api {

    //    private static final String TEST_POSTER_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    public static final String API_BASE_URL = "https://api.themoviedb.org/3";

    private static final String TAG = Api.class.getSimpleName();
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";
    private static final List<String> SUPPORTED_VIDE_SITES = Arrays.asList(VideoSite.YouTube);

    public static String apiKey() {
        return BuildConfig.TMDB_V3_API_KEY;
    }

    public static Uri.Builder baseUriBuilder() {
        return Uri.parse(API_BASE_URL)
                .buildUpon()
                .appendQueryParameter(Param.apiKey, apiKey());
    }

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

    public static ContentValues movieToContentValues(Movie movie) {

        ContentValues values = new ContentValues();

        values.put(MovieEntry._ID, movie.id);
        values.put(MovieEntry.COLUMN_TITLE, movie.title);
        values.put(MovieEntry.COLUMN_RELEASE_DATE, movie.releaseDate);
        values.put(MovieEntry.COLUMN_OVERVIEW, movie.overview);
        values.put(MovieEntry.COLUMN_POSTER_PATH, movie.posterPath);
        values.put(MovieEntry.COLUMN_BACKDROP_PATH, movie.backdropPath);
        values.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.voteAverage);
        values.put(MovieEntry.COLUMN_VOTE_COUNT, movie.voteCount);
        if (movie.isFavorite) {
            values.put(MovieEntry.COLUMN_IS_FAVORITE, 1);
            // Because bool becomes INT in SQL, and default is 0
        }

        return values;

    }

    public static String defaultCategory() {
        return Category.popular;
    }

    public static boolean isSupportedVideoSite(String site) {
        return SUPPORTED_VIDE_SITES.contains(site);
    }

    public static Uri getVideoUri(String site, String key) {
        Uri uri = null;
        switch (site) {
            case VideoSite.YouTube:
                return Uri.parse(VideoSiteUrl.YouTube)
                        .buildUpon()
                        .appendQueryParameter("v", key)
                        .build();
            default:
                throw new UnsupportedOperationException("Unknown video site: " + site);
        }

    }

    public static final class Param {
        public static final String apiKey = "api_key";
        public static final String sortBy = "sort_by";
        public static final String voteCount = "vote_count.gte";
    }

    public static final class Category {
        public static final String popular = "popular";
        public static final String topRated = "top_rated";
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

    public static final class VideoSite {
        public static final String YouTube = "YouTube";
    }

    private static final class VideoSiteUrl {
        public static final String YouTube = "https://www.youtube.com/watch";
    }
}
