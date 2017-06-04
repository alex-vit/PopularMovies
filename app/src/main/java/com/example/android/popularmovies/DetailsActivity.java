package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;

import java.text.DecimalFormat;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mYearTextView;
    private TextView mVotesTextView;
    private TextView mOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mTitleTextView = (TextView) findViewById(R.id.movie_title);
        mImageView = (ImageView) findViewById(R.id.poster_image);
        mYearTextView = (TextView) findViewById(R.id.movie_year);
        mVotesTextView = (TextView) findViewById(R.id.movie_votes);
        mOverviewTextView = (TextView) findViewById(R.id.movie_overview);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e(TAG, "No extras were passed.");
        } else {
            Movie movie = getIntent().getParcelableExtra("movie");

            mTitleTextView.setText(movie.title);

            String posterUrl = MovieService.fullImageUrl(movie.posterPath);
            Glide.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(mImageView);

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
