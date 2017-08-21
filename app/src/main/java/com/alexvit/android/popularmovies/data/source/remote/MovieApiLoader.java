package com.alexvit.android.popularmovies.data.source.remote;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.alexvit.android.popularmovies.data.Movie;
import com.alexvit.android.popularmovies.data.MovieListResponse;
import com.alexvit.android.popularmovies.utils.Prefs;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

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

        Call<MovieListResponse> call = MoviesRemoteDataSource.movies(mCategory);
        List<Movie> movies = null;
        try {
            MovieListResponse body = call.execute().body();
            if (body != null) {
                movies = body.movies;
            }
        } catch (IOException e) {
            FirebaseCrash.report(e);
            e.printStackTrace();
        }
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
