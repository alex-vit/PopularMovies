package com.alexvit.android.popularmovies.di.modules;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.di.qualifiers.ActivityContext;
import com.alexvit.android.popularmovies.di.scopes.ActivityScope;
import com.alexvit.android.popularmovies.moviedetails.DetailsViewModel;
import com.alexvit.android.popularmovies.movies.ListViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

@Module
public class ActivityModule {

    private final Context context;

    public ActivityModule(Activity activity) {
        this.context = activity;
    }

    @Provides
    @ActivityScope
    public ListViewModel listViewModel(MoviesRepository moviesRepository) {
        return new ListViewModel(moviesRepository);
    }

    @Provides
    @ActivityScope
    public DetailsViewModel detailsViewModel(MoviesRepository moviesRepository) {
        return new DetailsViewModel(moviesRepository);
    }

    @Provides
    @ActivityScope
    public SharedPreferences sharedPreferences(@ActivityContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context context() {
        return context;
    }
}
