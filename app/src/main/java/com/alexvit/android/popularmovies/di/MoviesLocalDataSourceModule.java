package com.alexvit.android.popularmovies.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.alexvit.android.popularmovies.data.source.local.MoviesDatabase;
import com.alexvit.android.popularmovies.data.source.local.MoviesLocalDataSource;
import com.alexvit.android.popularmovies.utils.Constants;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 8/31/2017.
 */

@Module(includes = {ContextModule.class})
public class MoviesLocalDataSourceModule {

    @Provides
    @ApplicationScope
    MoviesLocalDataSource moviesLocalDataSource(MoviesDatabase db) {
        return new MoviesLocalDataSource(db);
    }

    @Provides
    @ApplicationScope
    MoviesDatabase moviesDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, MoviesDatabase.class, Constants.DATABASE_FILE_NAME)
                .build();
    }
}
