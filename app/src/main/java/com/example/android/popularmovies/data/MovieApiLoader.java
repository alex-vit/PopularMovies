package com.example.android.popularmovies.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.popularmovies.PrefUtils;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.ListResponse;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static com.example.android.popularmovies.services.MovieService.API_BASE_URL;

/**
 * Created by Aleksandrs Vitjukovs on 7/16/2017.
 */

public class MovieApiLoader extends AsyncTaskLoader<List<Movie>> {

    // TODO: Does this need to listen to preference changes?
    // Like sort order changed during loading? O_o

    private static final String TAG = MovieApiLoader.class.getSimpleName();

    private String mSortBy = "";
    private List<Movie> mMovies = null;

    public MovieApiLoader(Context context) {
        super(context);
    }

    private static Uri.Builder baseUriBuilder(String apiKey) {
        return Uri
                .parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(MovieService.Param.apiKey, apiKey)
                .appendQueryParameter(MovieService.Param.voteCount, String.valueOf(100));
    }

    private static List<Movie> parseListResponse(String response) {
        ListResponse listResponse = new Gson().fromJson(response, ListResponse.class);
        return listResponse.movies;
    }

    @Override
    protected void onStartLoading() {

        String newSortBy = PrefUtils.getSortBy(getContext());

        if (mSortBy.equals(newSortBy) && mMovies != null) {
            deliverResult(mMovies);
        } else {
            mSortBy = newSortBy;
            forceLoad();
        }

    }

    @Override
    public void deliverResult(List<Movie> movies) {
        mMovies = movies;
        super.deliverResult(movies);
    }

    @Override
    public List<Movie> loadInBackground() {

        String apiKey = getContext().getString(R.string.themoviedb_api_v3_key);
        Uri uri = baseUriBuilder(apiKey)
                .appendEncodedPath("discover/movie")
                .appendQueryParameter(MovieService.Param.sortBy, mSortBy)
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

}