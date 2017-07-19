package com.example.android.popularmovies.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.MovieExtras;
import com.example.android.popularmovies.models.Review;
import com.example.android.popularmovies.models.ReviewListResponse;
import com.example.android.popularmovies.models.Video;
import com.example.android.popularmovies.models.VideoListResponse;
import com.example.android.popularmovies.util.Api;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static com.example.android.popularmovies.util.Api.API_BASE_URL;

/**
 * Created by Aleksandrs Vitjukovs on 7/18/2017.
 */

public class MovieExtrasApiLoader extends AsyncTaskLoader<MovieExtras> {

    private static final String TAG = MovieExtrasApiLoader.class.getSimpleName();

    private MovieExtras mExtras = null;
    private int mMovieId;

    public MovieExtrasApiLoader(Context context, int movieId) {
        super(context);
        this.mMovieId = movieId;
    }

    @Override
    protected void onStartLoading() {
        if (mExtras != null) {
            deliverResult(mExtras);
        } else {
            forceLoad();
        }
    }

    @Override
    public MovieExtras loadInBackground() {
        String apiKey = getContext().getString(R.string.themoviedb_api_v3_key);

        Uri uri = baseUriBuilder(apiKey)
                .appendEncodedPath("movie/" + mMovieId + "/reviews")
                .build();

        HttpURLConnection connection = null;
        List<Review> reviews = null;
        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            reviews = parseListResponse(scanner.next());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        uri = baseUriBuilder(apiKey)
                .appendEncodedPath("movie/" + mMovieId + "/videos")
                .build();
        connection = null;
        List<Video> videos = null;
        try {
            URL url = new URL(uri.toString());
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            videos = new Gson().fromJson(scanner.next(), VideoListResponse.class).videos;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }

        return new MovieExtras(reviews, videos);
    }

    @Override
    public void deliverResult(MovieExtras extras) {
        mExtras = extras;
        super.deliverResult(extras);
    }

    private static Uri.Builder baseUriBuilder(String apiKey) {
        return Uri
                .parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(Api.Param.apiKey, apiKey);
    }

    private static List<Review> parseListResponse(String response) {
        ReviewListResponse reviewListResponse = new Gson().fromJson(response, ReviewListResponse.class);
        return reviewListResponse.reviews;
    }

}