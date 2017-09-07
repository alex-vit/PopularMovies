package com.alexvit.android.popularmovies.di.components;

import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.di.modules.MoviesRepositoryModule;
import com.alexvit.android.popularmovies.di.scopes.ApplicationScope;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

@ApplicationScope
@Component(modules = {MoviesRepositoryModule.class})
public interface AppComponent {

    MoviesRepository moviesRepository();
}
