package com.alexvit.android.popularmovies.data.source.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.alexvit.android.popularmovies.PopularMoviesApplication;
import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 7/16/2017.
 */

public class MovieApiLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String TAG = MovieApiLoader.class.getSimpleName();

    private MoviesRemoteDataSource dataSource;
    private String mCategory = "";
    private List<Movie> mMovies = null;

    public MovieApiLoader(Context context) {
        super(context);
        this.dataSource = PopularMoviesApplication.get(context).getMoviesRemoteDataSource();
    }

    @Override
    protected void onStartLoading() {

        String newCategory = Prefs.getCategory(getContext());

        if (mCategory.equals(newCategory) && mMovies != null) {
            deliverResult(mMovies);
        } else {
            mCategory = newCategory;
            forceLoad();
        }

    }

    // TODO: Handle errors by tyoe and retry when connected to internet
    @Override
    public List<Movie> loadInBackground() {
        List<Movie> movies = dataSource.movies(mCategory)
                .onErrorReturn(__ -> new ArrayList<Movie>() {
                })
                .blockingSingle();
        return movies;
    }

    @Override
    public void deliverResult(List<Movie> movies) {
        if (movies != null) {
            mMovies = movies;
        }
        super.deliverResult(movies);
    }
}
