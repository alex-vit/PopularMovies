package com.alexvit.android.popularmovies.di.components;

import com.alexvit.android.popularmovies.di.modules.ActivityModule;
import com.alexvit.android.popularmovies.di.scopes.ActivityScope;
import com.alexvit.android.popularmovies.moviedetails.DetailsActivity;
import com.alexvit.android.popularmovies.movies.ListActivity;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = {AppComponent.class})
public interface ActivityComponent {

    void inject(ListActivity listActivity);

    void inject(DetailsActivity detailsActivity);
}
