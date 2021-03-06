package com.alexvit.android.popularmovies.data.source.remote;

import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.data.models.Review;
import com.alexvit.android.popularmovies.data.models.Video;

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

    public Observable<List<Movie>> moviesByPopularity() {
        return service.moviesByPopularity().map(r -> r.movies);
    }

    public Observable<List<Movie>> moviesByRating() {
        return service.moviesByRating().map(r -> r.movies);
    }

    public Observable<Movie> movieById(long movieId) {
        return service.movieById(movieId);
    }

    public Observable<List<Review>> reviewsByMovieId(long movieId) {
        return service.reviewsByMovieId(movieId)
                .map(resp -> {
                    final List<Review> reviews = resp.reviews;
                    for (Review r : reviews) {
                        r.movieId = resp.id;
                    }
                    return reviews;
                });
    }

    public Observable<List<Video>> videosByMovieId(long movieId) {
        return service.videosByMovieId(movieId).map(resp -> {
            final List<Video> videos = resp.videos;
            for (Video v : videos) {
                v.movieId = resp.id;
            }
            return videos;
        });
    }
}
