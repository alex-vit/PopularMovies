package com.alexvit.android.popularmovies.data;

import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.data.models.Review;
import com.alexvit.android.popularmovies.data.models.Video;
import com.alexvit.android.popularmovies.data.source.remote.MoviesRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

public class MoviesRepository {

    private final MoviesRemoteDataSource remoteDataSource;

    public MoviesRepository(MoviesRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public Observable<List<Movie>> moviesByCategory(String category) {
        return remoteDataSource.moviesByCategory(category);
    }

    public Observable<Movie> movieById(long movieId) {
        return remoteDataSource.movieById(movieId);
    }

    public Observable<List<Review>> reviewsByMovieId(long movieId) {
        return remoteDataSource.reviewsByMovieId(movieId);
    }

    public Observable<List<Video>> videosByMovieId(long movieId) {
        return remoteDataSource.videosByMovieId(movieId);
    }
}
