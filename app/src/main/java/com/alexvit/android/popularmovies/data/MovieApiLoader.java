package com.alexvit.android.popularmovies.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.alexvit.android.popularmovies.models.Movie;
import com.alexvit.android.popularmovies.models.MovieListResponse;
import com.alexvit.android.popularmovies.util.Api;
import com.alexvit.android.popularmovies.util.Prefs;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Aleksandrs Vitjukovs on 7/16/2017.
 */

public class MovieApiLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String TAG = MovieApiLoader.class.getSimpleName();

    private String mCategory = "";
    private List<Movie> mMovies = null;

    public MovieApiLoader(Context context) {
        super(context);
    }

    private static List<Movie> parseListResponse(String response) {
        MovieListResponse movieListResponse = new Gson().fromJson(response, MovieListResponse.class);
        return movieListResponse.movies;
    }

    @Override
    protected void onStartLoading() {

        String newCategory = Prefs.getCategory(getContext());

        if (mCategory.equals(newCategory) && mMovies != null) {
            deliverResult(mMovies);
        } else {
            mCategory = newCategory;
            forceLoad();
        }

    }

    @Override
    public List<Movie> loadInBackground() {

        Uri uri = Api.baseUriBuilder()
                .appendPath("movie")
                .appendPath(mCategory)
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

    @Override
    public void deliverResult(List<Movie> movies) {
        mMovies = movies;
        super.deliverResult(movies);
    }

}
