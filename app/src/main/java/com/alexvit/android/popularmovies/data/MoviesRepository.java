package com.alexvit.android.popularmovies.data;

import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.data.models.Review;
import com.alexvit.android.popularmovies.data.models.Video;
import com.alexvit.android.popularmovies.data.source.local.MoviesLocalDataSource;
import com.alexvit.android.popularmovies.data.source.remote.MoviesRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

public class MoviesRepository {

    private final MoviesLocalDataSource localDb;
    private final MoviesRemoteDataSource remoteDb;

    public MoviesRepository(MoviesLocalDataSource localDb,
                            MoviesRemoteDataSource remoteDb) {

        this.localDb = localDb;
        this.remoteDb = remoteDb;
    }

    public Observable<List<Movie>> moviesByFavorite() {
        return localDb.moviesByFavorite().toObservable();
    }

    public Observable<List<Movie>> moviesByPopularity() {

        remoteDb.moviesByPopularity()
                .subscribeOn(Schedulers.io())
                .subscribe(localDb::insert,
                        __ -> {
                        });

        return localDb.moviesByPopularity()
                .toObservable();
    }

    public Observable<List<Movie>> moviesByRating() {

        remoteDb.moviesByRating()
                .subscribeOn(Schedulers.io())
                .subscribe(localDb::insert,
                        __ -> {
                        });

        return localDb.moviesByRating()
                .toObservable();
    }

    public Observable<Movie> movieById(long movieId) {
        return localDb.movieById(movieId)
                .toObservable()
                .onErrorResumeNext(remoteDb.movieById(movieId).doOnNext(localDb::insert));
    }

    public Observable<List<Review>> reviewsByMovieId(long movieId) {
        return remoteDb.reviewsByMovieId(movieId);
    }

    public Observable<List<Video>> videosByMovieId(long movieId) {
        return remoteDb.videosByMovieId(movieId);
    }

    public void updateMovie(Movie movie) {
        localDb.update(movie);
    }
}
