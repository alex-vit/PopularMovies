package com.alexvit.android.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;

import com.alexvit.android.popularmovies.data.source.local.MovieContract;
import com.alexvit.android.popularmovies.data.source.remote.MovieExtrasApiLoader;
import com.alexvit.android.popularmovies.databinding.ActivityDetailsBinding;
import com.alexvit.android.popularmovies.data.Movie;
import com.alexvit.android.popularmovies.data.MovieExtras;
import com.alexvit.android.popularmovies.util.Api;
import com.bumptech.glide.Glide;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieExtras> {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final int EXTRA_LOADER_ID = 1200;

    private MovieExtrasAdapter mExtrasAdapter;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovie = getMovie();
        if (mMovie == null) {
            Log.e(TAG, "No movie was passed.");
            return;
        }

        ActivityDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        initToolbar(binding, mMovie.title);
        loadImages(binding, mMovie);
        loadText(binding, mMovie);
        setupFavorite(binding, mMovie.id);

        mExtrasAdapter = new MovieExtrasAdapter(binding.body.reviewList, binding.body.videoList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle args = new Bundle();
        args.putInt("movieId", mMovie.id);
        getSupportLoaderManager().initLoader(EXTRA_LOADER_ID, args, this);
    }

    @Override
    public Loader<MovieExtras> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case EXTRA_LOADER_ID:
                int movieId = args.getInt("movieId");
                return new MovieExtrasApiLoader(this, movieId);
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<MovieExtras> loader, MovieExtras extras) {
        mExtrasAdapter.setExtras(extras);
    }

    @Override
    public void onLoaderReset(Loader<MovieExtras> loader) {
        mExtrasAdapter.deleteExtras();
    }

    private Movie getMovie() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        } else {
            return getIntent().getParcelableExtra("movie");
        }
    }

    private void initToolbar(ActivityDetailsBinding binding, String title) {
        Toolbar toolbar = binding.appBar.toolbar;
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = binding.appBar.collapsingToolbar;
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary_dark));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white_text));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white_text));
        collapsingToolbarLayout.setTitle(title);
    }

    private void loadImages(ActivityDetailsBinding binding, Movie movie) {
        String posterSize = Api.PosterSize.w185;
        String backdropSize;
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            backdropSize = Api.BackdropSize.w780;
        } else {
            backdropSize = Api.BackdropSize.w300;
        }

        String posterUrl = Api.fullImageUrl(movie.posterPath, posterSize);
        Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .into(binding.body.ivPoster);

        String backdropUrl = Api.fullImageUrl(movie.backdropPath, backdropSize);
        Glide.with(this)
                .load(backdropUrl)
                .placeholder(R.drawable.placeholder_backdrop)
                .into(binding.appBar.ivBackdrop);
    }

    private void loadText(ActivityDetailsBinding binding, Movie movie) {
        binding.body.tvYear.setText(movie.year());
        binding.body.tvVotes.setText(
                "Rating: "
                        + new DecimalFormat("#0.0").format(movie.voteAverage)
                        + " (" + movie.voteCount.toString() + " votes)"
        );
        binding.body.tvOverview.setText(movie.overview);
    }

    private void setupFavorite(ActivityDetailsBinding binding, int movieId) {
        Uri uri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, movieId);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        boolean isFavorite = false;
        if (cursor != null && cursor.moveToFirst()) {
            isFavorite = (cursor.getInt(cursor.getColumnIndex(
                    MovieContract.MovieEntry.COLUMN_IS_FAVORITE)) == 1);
            cursor.close();
        }
        binding.body.toggleFavortie.setChecked(isFavorite);
        binding.body.toggleFavortie.setOnCheckedChangeListener(new FavoriteToggleListener(mMovie));
    }

    private class FavoriteToggleListener implements CompoundButton.OnCheckedChangeListener {

        private Movie movie;

        FavoriteToggleListener(Movie movie) {
            this.movie = movie;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                movie.isFavorite = true;
                ContentValues values = Api.movieToContentValues(movie);
                getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
            } else {
                getContentResolver().delete(
                        ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, movie.id),
                        null,
                        null);
            }
            getContentResolver().notifyChange(MovieContract.MovieEntry.CONTENT_URI, null);
        }
    }

}
