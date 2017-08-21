package com.alexvit.android.popularmovies.data.source.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.alexvit.android.popularmovies.data.MovieExtras;
import com.alexvit.android.popularmovies.data.Review;
import com.alexvit.android.popularmovies.data.Video;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;

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

        Observable<List<Review>> reviewsOb = MoviesRemoteDataSource.reviews(String.valueOf(mMovieId));
        Observable<List<Video>> videosOb = MoviesRemoteDataSource.videos(String.valueOf(mMovieId));

        Observable<MovieExtras> movieExtrasOb = reviewsOb.zipWith(videosOb, new BiFunction<List<Review>, List<Video>, MovieExtras>() {
            @Override
            public MovieExtras apply(@NonNull List<Review> reviews, @NonNull List<Video> videos) throws Exception {
                return new MovieExtras(reviews, videos);
            }
        });

        return movieExtrasOb.blockingSingle();
    }

    @Override
    public void deliverResult(MovieExtras extras) {
        mExtras = extras;
        super.deliverResult(extras);
    }

}
