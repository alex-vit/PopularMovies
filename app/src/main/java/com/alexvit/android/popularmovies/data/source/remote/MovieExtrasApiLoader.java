package com.alexvit.android.popularmovies.data.source.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.alexvit.android.popularmovies.data.MovieExtras;
import com.alexvit.android.popularmovies.data.Review;
import com.alexvit.android.popularmovies.data.ReviewListResponse;
import com.alexvit.android.popularmovies.data.Video;
import com.alexvit.android.popularmovies.data.VideoListResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

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

        Call<ReviewListResponse> callReviews = MoviesRemoteDataSource.reviews(String.valueOf(mMovieId));
        List<Review> reviews = null;
        try {
            ReviewListResponse body = callReviews.execute().body();
            if (body != null) {
                reviews = body.reviews;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Call<VideoListResponse> callVideos = MoviesRemoteDataSource.videos(String.valueOf(mMovieId));
        List<Video> videos = null;
        try {
            VideoListResponse body = callVideos.execute().body();
            if (body != null) {
                videos = body.videos;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new MovieExtras(reviews, videos);
    }

    @Override
    public void deliverResult(MovieExtras extras) {
        mExtras = extras;
        super.deliverResult(extras);
    }

}
