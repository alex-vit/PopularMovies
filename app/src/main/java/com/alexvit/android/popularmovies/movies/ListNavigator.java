package com.alexvit.android.popularmovies.movies;

import com.alexvit.android.popularmovies.base.BaseNavigator;
import com.alexvit.android.popularmovies.data.models.Movie;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

interface ListNavigator extends BaseNavigator {

    void onMoviesLoaded(List<Movie> movies);
}
