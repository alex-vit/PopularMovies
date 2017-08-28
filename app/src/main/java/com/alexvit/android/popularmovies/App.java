package com.alexvit.android.popularmovies;

import android.app.Application;
import android.content.Context;

import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.di.AppComponent;
import com.alexvit.android.popularmovies.di.ContextModule;
import com.alexvit.android.popularmovies.di.DaggerAppComponent;

import javax.inject.Inject;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

public class App extends Application {

    private AppComponent component;

    @Inject
    MoviesRepository moviesRepository;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();

        component.inject(this);
    }

    public AppComponent component() {
        return component;
    }

    public MoviesRepository getMoviesRepository() {
        return moviesRepository;
    }
}