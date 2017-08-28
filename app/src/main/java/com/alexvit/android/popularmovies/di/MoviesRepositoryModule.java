package com.alexvit.android.popularmovies.di;

import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.data.source.remote.MoviesRemoteDataSource;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

@Module(includes = {MoviesRemoteDataSourceModule.class})
public class MoviesRepositoryModule {

    @Provides
    @ApplicationScope
    public MoviesRepository moviesRepository(MoviesRemoteDataSource remoteDataSource) {
        return new MoviesRepository(remoteDataSource);
    }
}
