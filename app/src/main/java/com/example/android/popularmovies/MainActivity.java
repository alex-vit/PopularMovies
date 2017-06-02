package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.services.MovieService;

public class MainActivity extends AppCompatActivity implements MovieGridAdapter.MovieClickListener {

    private static final String TEST_POSTER_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    private static final int N_COLUMNS = 2;

    RecyclerView mMovieGridRecyclerView;
    private MovieGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        loadData();
    }

    private void loadData() {
        Movie[] movies = MovieService.getPopularMovies();
        mAdapter.setMovie(movies);
    }

    private void initRecyclerView() {
        mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.movie_grid);
        mMovieGridRecyclerView.setLayoutManager(new GridLayoutManager(this, N_COLUMNS));

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
}
