package com.example.android.popularmovies;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.databinding.ActivityDetailsBinding;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

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

            String posterUrl = MovieService.fullImageUrl(movie.posterPath, posterSize);
            Glide.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.ivPoster);
            String backdropUrl = MovieService.fullImageUrl(movie.backdropPath, backdropSize);
            Glide.with(this)
                    .load(backdropUrl)
                    .placeholder(R.drawable.placeholder_backdrop)
                    .into(binding.ivBackdrop);

            binding.tvYear.setText(movie.year());
            binding.tvVotes.setText(
                    "Rating: "
                            + new DecimalFormat("#0.0").format(movie.voteAverage)
                            + " (" + movie.voteCount.toString() + " votes)"
            );
            binding.tvOverview.setText(movie.overview);
        }
    }
}
