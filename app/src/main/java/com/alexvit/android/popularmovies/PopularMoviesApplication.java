package com.alexvit.android.popularmovies;

import android.app.Application;
import android.content.Context;

import com.alexvit.android.popularmovies.data.source.remote.MoviesRemoteDataSource;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

public class PopularMoviesApplication extends Application {

    public static PopularMoviesApplication get(Context context) {
        return (PopularMoviesApplication) context.getApplicationContext();
    }

    private MoviesRemoteDataSource moviesRemoteDataSource;

    @Override
    public void onCreate() {
        super.onCreate();

        PopularMoviesApplicationComponent component = DaggerPopularMoviesApplicationComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        moviesRemoteDataSource = component.getMoviesRemoteDataSource();
    }

    public MoviesRemoteDataSource getMoviesRemoteDataSource() {
        return moviesRemoteDataSource;
    }
}
