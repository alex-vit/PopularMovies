package com.alexvit.android.popularmovies.data.source.local;

import com.alexvit.android.popularmovies.data.models.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

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

    public Flowable<List<Movie>> favoriteMovies() {
        return db.movieDao().favoriteMovies();
    }

    public long insert(Movie movie) {
        return db.movieDao().insert(movie);
    }

    public void update(Movie movie) {
        Observable.fromCallable(() -> db.movieDao().update(movie))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
