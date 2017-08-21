package com.alexvit.android.popularmovies.data.source.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.alexvit.android.popularmovies.data.Movie;
import com.alexvit.android.popularmovies.utils.Prefs;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 7/16/2017.
 */

public class MovieApiLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String TAG = MovieApiLoader.class.getSimpleName();

    private String mCategory = "";
    private List<Movie> mMovies = null;

    public MovieApiLoader(Context context) {
        super(context);
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

    @Override
    public List<Movie> loadInBackground() {
        return MoviesRemoteDataSource.movies(mCategory).blockingSingle();
    }

    @Override
    public void deliverResult(List<Movie> movies) {
        if (movies != null) {
            mMovies = movies;
        }
        super.deliverResult(movies);
    }
}
