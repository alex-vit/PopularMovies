package com.alexvit.android.popularmovies.data.source.local;

import com.alexvit.android.popularmovies.data.models.Movie;

import io.reactivex.Single;

/**
 * Created by Aleksandrs Vitjukovs on 8/31/2017.
 */

public class MoviesLocalDataSource {

    private final MoviesDatabase db;

    public MoviesLocalDataSource(MoviesDatabase db) {
        this.db = db;
    }

    public Single<Movie> movieById(long id) {
        return db.movieDao().movieById(id);
    }

    public long insert(Movie movie) {
        return db.movieDao().insert(movie);
    }

    public int update(Movie movie) {
        return db.movieDao().update(movie);
    }
}
