package com.alexvit.android.popularmovies.data;

import android.util.Log;

import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.data.models.Review;
import com.alexvit.android.popularmovies.data.models.Video;
import com.alexvit.android.popularmovies.data.source.local.MoviesLocalDataSource;
import com.alexvit.android.popularmovies.data.source.remote.MoviesRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

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

    public Observable<List<Movie>> moviesByCategory(String category) {
        return remoteDb.moviesByCategory(category);
    }

    public Observable<Movie> movieById(long movieId) {
        return localDb.movieById(movieId)
                .toObservable()
                .onErrorResumeNext(remoteDb.movieById(movieId).map(this::cache));
    }

    public Observable<List<Review>> reviewsByMovieId(long movieId) {
        return remoteDb.reviewsByMovieId(movieId);
    }

    public Observable<List<Video>> videosByMovieId(long movieId) {
        return remoteDb.videosByMovieId(movieId);
    }

    public int updateMovie(Movie movie) {
        return localDb.update(movie);
    }

    private Movie cache(Movie movie) {
        localDb.insert(movie);
        Log.d("Repo", "caching " + movie.id);
        return movie;
    }
}
