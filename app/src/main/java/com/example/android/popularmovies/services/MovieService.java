package com.example.android.popularmovies.services;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmovies.models.ListResponse;
import com.example.android.popularmovies.models.Movie;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Aleksandrs Vitjukovs on 6/2/2017.
 */

public class MovieService {

    private static final String TAG = MovieService.class.getSimpleName();
    //    private static final String TEST_POSTER_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    private static final String API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
    private final String apiKey;

    public MovieService(String apiKey) {
        this.apiKey = apiKey;
    }

    private static List<Movie> parseListResponse(String response) {
        ListResponse listResponse = new Gson().fromJson(response, ListResponse.class);
        Log.d(TAG, "Parsed movies: " + listResponse.movies.size());
        return listResponse.movies;
    }

    public static String fullPosterUrl(String posterPath) {
        return POSTER_BASE_URL + posterPath;
    }

    private Uri.Builder baseUriBuilder() {
        return Uri
                .parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(Param.apiKey, apiKey)
                .appendQueryParameter(Param.voteCount, String.valueOf(100));
    }

    public List<Movie> getMovies(String sortBy) {

        Uri uri = baseUriBuilder()
                .appendEncodedPath("discover/movie")
                .appendQueryParameter(Param.sortBy, sortBy)
                .build();

        Log.d(TAG, "Built URI: " + uri);

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
    }
}
