package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.android.popularmovies.models.Movie;

import static com.example.android.popularmovies.utils.ImageUtils.loadUrlIntoImageView;

public class DetailsActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Movie movie = getIntent().getParcelableExtra("movie");
        mImageView = (ImageView) findViewById(R.id.poster_image);

        loadUrlIntoImageView(this, movie.getPosterUrl(), R.drawable.placeholder, mImageView);
    }
}
