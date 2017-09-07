package com.alexvit.android.popularmovies.di.modules;

import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.data.source.local.MoviesLocalDataSource;
import com.alexvit.android.popularmovies.data.source.remote.MoviesRemoteDataSource;
import com.alexvit.android.popularmovies.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

@Module(includes = {MoviesLocalDataSourceModule.class, MoviesRemoteDataSourceModule.class})
public class MoviesRepositoryModule {

    @Provides
    @ApplicationScope
    public MoviesRepository moviesRepository(MoviesLocalDataSource localDataSource, MoviesRemoteDataSource remoteDataSource) {
        return new MoviesRepository(localDataSource, remoteDataSource);
    }
}
