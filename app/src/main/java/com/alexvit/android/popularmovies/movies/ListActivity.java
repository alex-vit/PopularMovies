package com.alexvit.android.popularmovies.movies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.alexvit.android.popularmovies.moviedetails.DetailsActivity;
import com.alexvit.android.popularmovies.R;
import com.alexvit.android.popularmovies.settings.SettingsActivity;
import com.alexvit.android.popularmovies.data.source.remote.MovieApiLoader;
import com.alexvit.android.popularmovies.data.source.local.MovieSqlLoader;
import com.alexvit.android.popularmovies.data.Movie;
import com.alexvit.android.popularmovies.utils.Movies;
import com.alexvit.android.popularmovies.utils.Prefs;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity
        implements MovieGridAdapter.MovieClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener,
        LoaderManager.LoaderCallbacks {

    private static final String TAG = ListActivity.class.getSimpleName();
    private static final int MOVIE_API_LOADER_ID = 1000;
    private static final int MOVIE_SQL_LOADER_ID = 1100;

    @BindView(R.id.movie_grid)
    RecyclerView mMovieGridRecyclerView;

    private MovieGridAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private String mCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        mCategory = Prefs.getCategory(this);
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mAdapter.getItemCount() == 0) {
            // Reload if there's nothing to show (orientation changed)
            // Always reload favorites, so if you open a favorite, unfavorite it and go back, it disappears
            reload();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
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
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_category_key))) {
            String newSortBy = sharedPreferences.getString(key, Movies.defaultCategory());
            if (!newSortBy.equals(mCategory)) {
                // Sorting changed, should reload data.
                mCategory = newSortBy;
                reload();
            }
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case MOVIE_API_LOADER_ID:
                return new MovieApiLoader(this);
            case MOVIE_SQL_LOADER_ID:
                return new MovieSqlLoader(this);
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (data instanceof List) {
            List<Movie> movies = (List<Movie>) data;
            mAdapter.setMovies(movies);
        } else {
            Cursor cursor = (Cursor) data;
            mAdapter.setMovies(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.deleteMovies();
    }

    private void initRecyclerView() {
        final int nColumns = getResources().getInteger(R.integer.grid_column_count);
        mMovieGridRecyclerView.setLayoutManager(new GridLayoutManager(this, nColumns));

        mAdapter = new MovieGridAdapter(this);
        mMovieGridRecyclerView.setAdapter(mAdapter);

        mMovieGridRecyclerView.setHasFixedSize(true);
    }

    private void reload() {
        mAdapter.deleteMovies();
        CharSequence title = Prefs.getCategoryTitle(this, mCategory);
        setTitle(title);

        // Technically, "query" never changes, so init, don't restart ( == destroy, create)
        if (mCategory.equals(getString(R.string.pref_category_favorite))) {
            getSupportLoaderManager().destroyLoader(MOVIE_API_LOADER_ID);
            getSupportLoaderManager().initLoader(MOVIE_SQL_LOADER_ID, null, this);
        } else {
            getSupportLoaderManager().destroyLoader(MOVIE_SQL_LOADER_ID);
            getSupportLoaderManager().initLoader(MOVIE_API_LOADER_ID, null, this);
        }
    }

}
