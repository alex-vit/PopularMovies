package com.alexvit.android.popularmovies.data.source.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.alexvit.android.popularmovies.data.MovieExtras;
import com.alexvit.android.popularmovies.data.Review;
import com.alexvit.android.popularmovies.data.Video;

import java.util.ArrayList;
import java.util.List;

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

        List<Review> reviews = MoviesRemoteDataSource
                .reviews(String.valueOf(mMovieId))
                .onErrorReturn(__ -> new ArrayList<Review>() {
                })
                .blockingSingle();
        List<Video> videos = MoviesRemoteDataSource
                .videos(String.valueOf(mMovieId))
                .onErrorReturn(__ -> new ArrayList<Video>() {
                })
                .blockingSingle();


        return new MovieExtras(reviews, videos);
    }

    @Override
    public void deliverResult(MovieExtras extras) {
        mExtras = extras;
        super.deliverResult(extras);
    }

}
