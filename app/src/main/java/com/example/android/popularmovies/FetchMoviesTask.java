package com.example.android.popularmovies;

import android.os.AsyncTask;

import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 6/4/2017.
 */

public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    private MovieService mMovieService;
    private MovieGridAdapter mAdapter;

    public FetchMoviesTask(MovieService movieService, MovieGridAdapter movieGridAdapter) {
        this.mMovieService = movieService;
        this.mAdapter = movieGridAdapter;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        String sortBy = params[0];
        return mMovieService.getMovies(sortBy);
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        mAdapter.setMovies(movies);
    }
}
