package com.example.android.popularmovies.services;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.MovieContract.MovieEntry;
import com.example.android.popularmovies.models.ListResponse;
import com.example.android.popularmovies.models.Movie;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Aleksandrs Vitjukovs on 6/2/2017.
 */

public class MovieService {

    private static final String TAG = MovieService.class.getSimpleName();
    //    private static final String TEST_POSTER_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    private static final String API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";
    private final String apiKey;
    private final Context context;

    public MovieService(Context context) {
        this.context = context;
        this.apiKey = context.getString(R.string.themoviedb_api_v3_key);
    }

    private static List<Movie> parseListResponse(String response) {
        ListResponse listResponse = new Gson().fromJson(response, ListResponse.class);
        Log.d(TAG, "Parsed movies: " + listResponse.movies.size());
        return listResponse.movies;
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

    private static Movie movieFromCursor(Cursor cursor) {
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

    private Uri.Builder baseUriBuilder() {
        return Uri
                .parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(Param.apiKey, apiKey)
                .appendQueryParameter(Param.voteCount, String.valueOf(100));
    }

    public List<Movie> getMovies(String sortBy) {

        if (sortBy.equals(SortBy.favorite)) {
            return getFavorites();
        }

        Uri uri = baseUriBuilder()
                .appendEncodedPath("discover/movie")
                .appendQueryParameter(Param.sortBy, sortBy)
                .build();

        HttpURLConnection connection = null;
        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            return parseListResponse(scanner.next());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (connection != null) connection.disconnect();
        }

        return null;

    }

    private List<Movie> getFavorites() {
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                "isFavorite = ?",
                new String[]{"1"},
                null
        );

        List<Movie> movies = new ArrayList<>();
        while (cursor.moveToNext()) {
            movies.add(movieFromCursor(cursor));
        }
        return movies;
    }

    public String defaultSorting() {
        return SortBy.popularityDesc;
    }

    private static final class Param {
        private static final String apiKey = "api_key";
        private static final String sortBy = "sort_by";
        private static final String voteCount = "vote_count.gte";
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
