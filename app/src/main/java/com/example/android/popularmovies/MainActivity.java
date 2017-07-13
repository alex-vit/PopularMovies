package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements MovieGridAdapter.MovieClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    RecyclerView mMovieGridRecyclerView;
    private String mCurrentSortOrder = null;
    private MovieService mMovieService;
    private MovieGridAdapter mAdapter;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieService = new MovieService(getString(R.string.themoviedb_api_v3_key));
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // TODO: Maybe abstract this into a setFromStateOrPrefs method
        if (savedInstanceState != null) {
            mCurrentSortOrder = savedInstanceState.getString(getString(R.string.pref_sort_order_key));
        }

        if (mCurrentSortOrder == null) {
            mCurrentSortOrder = mSharedPreferences.getString(
                    getString(R.string.pref_sort_order_key),
                    MovieService.SortBy.popularityDesc
            );
        }

        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        initRecyclerView();
        loadData();
    }

    private void loadData() {
        String sortBy = mCurrentSortOrder;
        CharSequence[] values = getResources().getStringArray(R.array.pref_sort_by_values);
        CharSequence[] titles = getResources().getStringArray(R.array.pref_sort_by_entries);
        int idx = Arrays.asList(values).indexOf(sortBy);
        CharSequence sortByTitle = titles[idx];
        setTitle(sortByTitle);

        loadMovies(sortBy);
    }

    private void loadMovies(String sortBy) {
        new FetchMoviesTask(mMovieService, mAdapter).execute(sortBy);
    }

    private void initRecyclerView() {
        mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.movie_grid);

        final int nColumns = getResources().getInteger(R.integer.grid_column_count);
        mMovieGridRecyclerView.setLayoutManager(new GridLayoutManager(this, nColumns));

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
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(getString(R.string.pref_sort_order_key), mCurrentSortOrder);
        super.onSaveInstanceState(outState);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_order_key))) {
            String newSortOrder = sharedPreferences.getString(key, MovieService.SortBy.popularityDesc);
            if (!newSortOrder.equals(mCurrentSortOrder)) {
                mCurrentSortOrder = newSortOrder;
                loadData();
            }
        }

    }
}
