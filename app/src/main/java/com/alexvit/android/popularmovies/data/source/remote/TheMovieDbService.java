package com.alexvit.android.popularmovies.data.source.remote;

import com.alexvit.android.popularmovies.data.MovieListResponse;
import com.alexvit.android.popularmovies.data.ReviewListResponse;
import com.alexvit.android.popularmovies.data.VideoListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Aleksandrs Vitjukovs on 8/21/2017.
 */

public interface TheMovieDbService {

    @GET("movie/{category}")
    Call<MovieListResponse> movies(@Path("category") String category);

    @GET("movie/{movieId}/reviews")
    Call<ReviewListResponse> reviews(@Path("movieId") String movieId);

    @GET("movie/{movieId}/videos")
    Call<VideoListResponse> videos(@Path("movieId") String movieId);

}
