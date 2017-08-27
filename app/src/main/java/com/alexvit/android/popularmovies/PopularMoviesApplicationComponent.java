package com.alexvit.android.popularmovies;

import com.alexvit.android.popularmovies.data.source.remote.MoviesRemoteDataSource;
import com.bumptech.glide.Glide;

import dagger.Component;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

@Component(modules = {GlideModule.class, MoviesRemoteDataSourceModule.class})
public interface PopularMoviesApplicationComponent {
    Glide getGlide();

    MoviesRemoteDataSource getMoviesRemoteDataSource();
}
