package com.alexvit.android.popularmovies;

import android.app.Application;
import android.content.Context;

import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.di.ContextModule;
import com.alexvit.android.popularmovies.di.DaggerAppComponent;

import javax.inject.Inject;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

public class App extends Application {

    @Inject
    MoviesRepository moviesRepository;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build()
                .inject(this);
    }

    public MoviesRepository getMoviesRepository() {
        return moviesRepository;
    }
}
