package com.alexvit.android.popularmovies;

import android.app.Application;
import android.content.Context;

import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.di.ContextModule;
import com.alexvit.android.popularmovies.di.DaggerPopularMoviesApplicationComponent;
import com.alexvit.android.popularmovies.di.PopularMoviesApplicationComponent;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

public class PopularMoviesApplication extends Application {

    private MoviesRepository moviesRepository;

    public static PopularMoviesApplication get(Context context) {
        return (PopularMoviesApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        PopularMoviesApplicationComponent component = DaggerPopularMoviesApplicationComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

        moviesRepository = component.getMoviesRepository();
    }

    public MoviesRepository getMoviesRepository() {
        return moviesRepository;
    }
}
