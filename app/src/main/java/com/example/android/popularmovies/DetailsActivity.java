package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.android.popularmovies.models.MovieData;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        MovieData movie = getIntent().getParcelableExtra("movie");
        mImageView = (ImageView) findViewById(R.id.poster_image);

        Picasso
                .with(this)
                .load(movie.getPosterUrl())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(mImageView);
    }
}
