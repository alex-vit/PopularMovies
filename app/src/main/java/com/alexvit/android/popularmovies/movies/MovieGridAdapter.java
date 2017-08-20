package com.alexvit.android.popularmovies.movies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexvit.android.popularmovies.R;
import com.alexvit.android.popularmovies.data.Movie;
import com.alexvit.android.popularmovies.utils.Api;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aleksandrs Vitjukovs on 5/31/2017.
 */

class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridAdapterViewHolder> {

    private static final String TAG = MovieGridAdapter.class.getSimpleName();
    private final MovieClickListener mMovieClickListener;
    private List<Movie> movieList;
    private Cursor movieCursor;
    private Mode mode = Mode.NoData;

    MovieGridAdapter(MovieClickListener mMovieClickListener) {
        this.mMovieClickListener = mMovieClickListener;
    }

    @Override
    public MovieGridAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int itemLayoutId = R.layout.movie_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(itemLayoutId, parent, false);
        return new MovieGridAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieGridAdapterViewHolder holder, int position) {

        Movie movie = getData(position);
        if (movie == null) return;

        String posterUrl = Api.fullImageUrl(movie.posterPath);
        Glide.with(holder.ivPoster.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        switch (mode) {
            case Cursor:
                return (movieCursor == null) ? 0 : movieCursor.getCount();
            case List:
                return movieList.size();
            default:
                return 0;
        }
    }

    public synchronized Mode getMode() {
        return mode;
    }

    synchronized void setMovies(List<Movie> movies) {
        movieList = movies;
        movieCursor = null;
        mode = Mode.List;
        notifyDataSetChanged();
    }

    synchronized void setMovies(Cursor cursor) {
        movieCursor = cursor;
        movieList = null;
        mode = Mode.Cursor;
        notifyDataSetChanged();
    }

    synchronized void deleteMovies() {
        movieCursor = null;
        movieList = null;
        mode = Mode.NoData;
        notifyDataSetChanged();
    }

    private Movie getData(int position) {
        switch (mode) {
            case Cursor:
                movieCursor.moveToPosition(position);
                return Api.movieFromCursor(movieCursor);
            case List:
                return movieList.get(position);
            default:
                return null;
        }
    }

    public enum Mode {
        List, Cursor, NoData
    }

    interface MovieClickListener {
        void onMovieClicked(Movie movie);
    }

    class MovieGridAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_poster)
        ImageView ivPoster;

        MovieGridAdapterViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = getData(position);
            mMovieClickListener.onMovieClicked(movie);
        }
    }
}
