package com.example.android.popularmovies.services;

import com.example.android.popularmovies.models.Movie;

/**
 * Created by Aleksandrs Vitjukovs on 6/2/2017.
 */

public class MovieService {

    private static final String TEST_POSTER_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

    public static Movie[] getPopularMovies() {
        return new Movie[]{
                new Movie(TEST_POSTER_URL),
                new Movie(TEST_POSTER_URL),
                new Movie(TEST_POSTER_URL),
                new Movie(TEST_POSTER_URL),
                new Movie(TEST_POSTER_URL),
                new Movie(TEST_POSTER_URL),
                new Movie(TEST_POSTER_URL)
        };
    }
}
