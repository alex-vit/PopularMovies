package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;

public class DetailsActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mYearTextView;
    private TextView mOverviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mTitleTextView = (TextView) findViewById(R.id.movie_title);
        mImageView = (ImageView) findViewById(R.id.poster_image);
        mYearTextView = (TextView) findViewById(R.id.movie_year);
        mOverviewTextView = (TextView) findViewById(R.id.movie_overview);

        Movie movie = getIntent().getParcelableExtra("movie");

        mTitleTextView.setText(movie.title);

        String posterUrl = MovieService.fullPosterUrl(movie.posterPath);
        Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .into(mImageView);

        mYearTextView.setText(movie.year());
        mOverviewTextView.setText(movie.overview);
    }
}
