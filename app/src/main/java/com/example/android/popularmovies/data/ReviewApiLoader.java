package com.example.android.popularmovies.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.models.Review;
import com.example.android.popularmovies.models.ReviewListResponse;
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

public class ReviewApiLoader extends AsyncTaskLoader<List<Review>> {

    private static final String TAG = ReviewApiLoader.class.getSimpleName();

    private List<Review> mReviews = null;
    private int mMovieId;

    public ReviewApiLoader(Context context, int movieId) {
        super(context);
        this.mMovieId = movieId;
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

    @Override
    protected void onStartLoading() {
        if (mReviews != null) {
            deliverResult(mReviews);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Review> loadInBackground() {
        String apiKey = getContext().getString(R.string.themoviedb_api_v3_key);

        Uri uri = baseUriBuilder(apiKey)
                .appendEncodedPath("movie/" + mMovieId + "/reviews")
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
    public void deliverResult(List<Review> reviews) {
        mReviews = reviews;
        super.deliverResult(reviews);
    }

}
