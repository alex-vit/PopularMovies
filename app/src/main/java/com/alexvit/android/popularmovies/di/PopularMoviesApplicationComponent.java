package com.alexvit.android.popularmovies.di;

import com.alexvit.android.popularmovies.data.source.remote.MoviesRemoteDataSource;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

@ApplicationScope
@Component(modules = {MoviesRemoteDataSourceModule.class})
public interface PopularMoviesApplicationComponent {

    MoviesRemoteDataSource getMoviesRemoteDataSource();
}
