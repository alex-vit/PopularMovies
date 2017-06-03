package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieGridAdapter.MovieClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int N_COLUMNS = 2;
    RecyclerView mMovieGridRecyclerView;
    private MovieService mMovieService;
    private MovieGridAdapter mAdapter;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieService = new MovieService(getString(R.string.themoviedb_api_v3_key));
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        initRecyclerView();
        loadData();
    }

    private void loadData() {
        String sortBy = mSharedPreferences.getString(
                getString(R.string.pref_sort_order_key),
                MovieService.SortBy.popularityDesc
        );
        CharSequence[] values = getResources().getStringArray(R.array.pref_sort_by_values);
        CharSequence[] titles = getResources().getStringArray(R.array.pref_sort_by_entries);
        int idx = Arrays.asList(values).indexOf(sortBy);
        CharSequence sortByTitle = titles[idx];
        setTitle(sortByTitle);

        loadMovies(sortBy);
        Log.d(TAG, "Sort option: " + sortBy);
    }

    private void loadMovies(String sortBy) {
        new FetchMoviesTask().execute(sortBy);
    }

    private void initRecyclerView() {
        mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.movie_grid);
        mMovieGridRecyclerView.setLayoutManager(new GridLayoutManager(this, N_COLUMNS));

        mAdapter = new MovieGridAdapter(this);
        mMovieGridRecyclerView.setAdapter(mAdapter);

        mMovieGridRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
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
}
