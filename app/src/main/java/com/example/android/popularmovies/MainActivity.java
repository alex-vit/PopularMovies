package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.popularmovies.models.MovieData;

public class MainActivity extends AppCompatActivity {

    private static final String TEST_POSTER_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    private static final int N_COLUMNS = 2;

    private RecyclerView mMovieGridRecyclerView;
    private MovieGridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();
        loadData();
    }

    private void loadData() {
        MovieData[] fakeData = {
                new MovieData(TEST_POSTER_URL),
                new MovieData(TEST_POSTER_URL),
                new MovieData(TEST_POSTER_URL),
                new MovieData(TEST_POSTER_URL),
                new MovieData(TEST_POSTER_URL),
                new MovieData(TEST_POSTER_URL),
                new MovieData(TEST_POSTER_URL)
        };

        mAdapter.setMovieData(fakeData);
    }

    private void initRecyclerView() {
        mMovieGridRecyclerView = (RecyclerView) findViewById(R.id.movie_grid);
        mMovieGridRecyclerView.setLayoutManager(new GridLayoutManager(this, N_COLUMNS));

        mAdapter = new MovieGridAdapter();
        mMovieGridRecyclerView.setAdapter(mAdapter);

        mMovieGridRecyclerView.setHasFixedSize(true);
    }
}
