package com.alexvit.android.popularmovies.di;

import com.alexvit.android.popularmovies.data.MoviesRepository;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

@ApplicationScope
@Component(modules = {MoviesRepositoryModule.class})
public interface PopularMoviesApplicationComponent {

    MoviesRepository getMoviesRepository();
}
