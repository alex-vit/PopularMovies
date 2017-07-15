package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.data.MovieApiLoader;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MovieGridAdapter.MovieClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener,
        LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_API_LOADER_ID = 1001;

    RecyclerView mMovieGridRecyclerView;
    private String mCurrentSortOrder = null;
    private MovieService mMovieService;
    private MovieGridAdapter mAdapter;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieService = new MovieService(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        initRecyclerView();
        getSupportLoaderManager().initLoader(MOVIE_API_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO Use content loader and restart it here. Currently, if you go to movie detail
        // and un-favorite, need to manually refresh.
        getSupportLoaderManager().restartLoader(MOVIE_API_LOADER_ID, null, this);
//        if (mCurrentSortOrder.equals(MovieService.SortBy.favorite)) loadData();
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

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case MOVIE_API_LOADER_ID:
                return new MovieApiLoader(this);
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        String sortBy = PrefUtils.getSortBy(this);
        CharSequence title = PrefUtils.getSortByTitle(this, sortBy);
        setTitle(title);
        mAdapter.setMovies(movies);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mAdapter.setMovies(null);
    }
}
