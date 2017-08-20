package com.alexvit.android.popularmovies.moviedetails;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alexvit.android.popularmovies.R;
import com.alexvit.android.popularmovies.data.source.local.MovieContract;
import com.alexvit.android.popularmovies.data.source.remote.MovieExtrasApiLoader;
import com.alexvit.android.popularmovies.data.Movie;
import com.alexvit.android.popularmovies.data.MovieExtras;
import com.alexvit.android.popularmovies.utils.Api;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieExtras> {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final int EXTRA_LOADER_ID = 1200;
    
    @BindView(R.id.app_bar)
    AppBarLayout incAppBar;
    @BindView(R.id.body)
    View incBody;

    private AppBar mAppBar;
    private Body mBody;

    private MovieExtrasAdapter mExtrasAdapter;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mMovie = getMovie();
        if (mMovie == null) {
            Log.e(TAG, "No movie was passed.");
            return;
        }

        ButterKnife.bind(this);
        
        mAppBar = new AppBar();
        ButterKnife.bind(mAppBar, incAppBar);
        mBody = new Body();
        ButterKnife.bind(mBody, incBody);

        initToolbar(mMovie.title);
        loadImages(mMovie);
        loadText(mMovie);
        setupFavorite(mMovie.id);

        mExtrasAdapter = new MovieExtrasAdapter(mBody.reviewList, mBody.videoList);
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

    private void initToolbar(String title) {
        setSupportActionBar(mAppBar.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAppBar.collapsingToolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.primary_dark));
        mAppBar.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white_text));
        mAppBar.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white_text));
        mAppBar.collapsingToolbar.setTitle(title);
    }

    private void loadImages(Movie movie) {
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
                .into(mBody.ivPoster);

        String backdropUrl = Api.fullImageUrl(movie.backdropPath, backdropSize);
        Glide.with(this)
                .load(backdropUrl)
                .placeholder(R.drawable.placeholder_backdrop)
                .into(mAppBar.ivBackdrop);
    }

    private void loadText(Movie movie) {
        mBody.tvYear.setText(movie.year());
        mBody.tvVotes.setText(
                "Rating: "
                        + new DecimalFormat("#0.0").format(movie.voteAverage)
                        + " (" + movie.voteCount.toString() + " votes)"
        );
        mBody.tvOverview.setText(movie.overview);
    }

    private void setupFavorite(int movieId) {
        Uri uri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, movieId);
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        boolean isFavorite = false;
        if (cursor != null && cursor.moveToFirst()) {
            isFavorite = (cursor.getInt(cursor.getColumnIndex(
                    MovieContract.MovieEntry.COLUMN_IS_FAVORITE)) == 1);
            cursor.close();
        }
        mBody.toggleFavorite.setChecked(isFavorite);
        mBody.toggleFavorite.setOnCheckedChangeListener(new FavoriteToggleListener(mMovie));
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

    static class AppBar {
        @BindView(R.id.collapsing_toolbar)
        CollapsingToolbarLayout collapsingToolbar;
        @BindView(R.id.iv_backdrop)
        ImageView ivBackdrop;
        @BindView(R.id.toolbar)
        Toolbar toolbar;
    }

    static class Body {
        @BindView(R.id.iv_poster)
        ImageView ivPoster;
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.tv_votes)
        TextView tvVotes;
        @BindView(R.id.tv_overview)
        TextView tvOverview;
        @BindView(R.id.video_list)
        FlexboxLayout videoList;
        @BindView(R.id.toggle_favortie)
        ToggleButton toggleFavorite;
        @BindView(R.id.review_list)
        LinearLayout reviewList;
    }
}
