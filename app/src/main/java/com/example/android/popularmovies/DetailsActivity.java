package com.example.android.popularmovies;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private ImageView mImageView;
    private TextView mYearTextView;
    private TextView mVotesTextView;
    private TextView mOverviewTextView;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mBackdropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mImageView = (ImageView) findViewById(R.id.poster_image);
        mYearTextView = (TextView) findViewById(R.id.movie_year);
        mVotesTextView = (TextView) findViewById(R.id.movie_votes);
        mOverviewTextView = (TextView) findViewById(R.id.movie_overview);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mBackdropImageView = (ImageView) findViewById(R.id.backdrop);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCollapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary_dark));
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white_text));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white_text));

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e(TAG, "No extras were passed.");
        } else {
            Movie movie = getIntent().getParcelableExtra("movie");

            String posterSize;
            String backdropSize;

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                posterSize = MovieService.PosterSize.w342;
                backdropSize = MovieService.BackdropSize.w780;
            } else {
                posterSize = MovieService.PosterSize.w185;
                backdropSize = MovieService.BackdropSize.w300;
            }

            mCollapsingToolbarLayout.setTitle(movie.title);

            String posterUrl = MovieService.fullImageUrl(movie.posterPath);
            Glide.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(mImageView);
            String backdropUrl = MovieService.fullImageUrl(movie.backdropPath, MovieService.BackdropSize.w300);
            Glide.with(this)
                    .load(backdropUrl)
                    .into(mBackdropImageView);

            mYearTextView.setText(movie.year());
            mVotesTextView.setText(
                    "Rating: "
                            + new DecimalFormat("#0.0").format(movie.voteAverage)
                            + " (" + movie.voteCount.toString() + " votes)"
            );
            mOverviewTextView.setText(movie.overview);
        }
    }
}
