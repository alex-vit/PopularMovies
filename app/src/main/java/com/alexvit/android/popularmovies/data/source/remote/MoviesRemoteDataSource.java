package com.alexvit.android.popularmovies.data.source.remote;

import com.alexvit.android.popularmovies.data.Movie;
import com.alexvit.android.popularmovies.data.Review;
import com.alexvit.android.popularmovies.data.Video;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Aleksandrs Vitjukovs on 8/21/2017.
 */

public final class MoviesRemoteDataSource {

    private final TheMovieDbService service;

    public MoviesRemoteDataSource(TheMovieDbService service) {
        this.service = service;
    }

    public Observable<List<Movie>> movies(String category) {
        return service.movies(category).map(r -> r.movies);
    }

    public Observable<List<Review>> reviews(String movieId) {
        return service.reviews(movieId).map(r -> r.reviews);
    }

    public Observable<List<Video>> videos(String movieId) {
        return service.videos(movieId).map(r -> r.videos);
    }

}
