package com.alexvit.android.popularmovies.data.source.remote;

import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.data.models.api.MovieListResponse;
import com.alexvit.android.popularmovies.data.models.api.ReviewListResponse;
import com.alexvit.android.popularmovies.data.models.api.VideoListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Aleksandrs Vitjukovs on 8/21/2017.
 */

public interface TheMovieDbService {

    @GET("movie/popular")
    Observable<MovieListResponse> moviesByPopularity();

    @GET("movie/top_rated")
    Observable<MovieListResponse> moviesByRating();

    @GET("movie/{movieId}")
    Observable<Movie> movieById(@Path("movieId") long movieId);

    @GET("movie/{movieId}/reviews")
    Observable<ReviewListResponse> reviewsByMovieId(@Path("movieId") long movieId);

    @GET("movie/{movieId}/videos")
    Observable<VideoListResponse> videosByMovieId(@Path("movieId") long movieId);

}
