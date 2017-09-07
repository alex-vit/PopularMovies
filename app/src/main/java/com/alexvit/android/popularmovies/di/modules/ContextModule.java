package com.alexvit.android.popularmovies.di.modules;

import android.content.Context;

import com.alexvit.android.popularmovies.di.qualifiers.ApplicationContext;
import com.alexvit.android.popularmovies.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context context() {
        return context;
    }
}
