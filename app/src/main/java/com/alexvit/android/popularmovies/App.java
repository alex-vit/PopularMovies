package com.alexvit.android.popularmovies;

import android.app.Application;
import android.content.Context;

import com.alexvit.android.popularmovies.di.DaggerAppComponent;
import com.alexvit.android.popularmovies.di.components.AppComponent;
import com.alexvit.android.popularmovies.di.modules.ContextModule;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

public class App extends Application {

    private AppComponent component;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public AppComponent component() {
        return component;
    }
}
