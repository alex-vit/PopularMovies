package com.alexvit.android.popularmovies.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexvit.android.popularmovies.R;
import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.utils.Movies;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aleksandrs Vitjukovs on 5/31/2017.
 */

class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridAdapterViewHolder> {

    @SuppressWarnings("unused")
    private static final String TAG = MovieGridAdapter.class.getSimpleName();

    private final MovieClickListener mMovieClickListener;
    private List<Movie> movieList;

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

        String posterUrl = Movies.fullImageUrl(movie.posterPath);
        Glide.with(holder.ivPoster.getContext())
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return (movieList == null) ? 0 : movieList.size();
    }

    void setMovies(List<Movie> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }

    synchronized void deleteMovies() {
        movieList = null;
        notifyDataSetChanged();
    }

    private Movie getData(int position) {
        return movieList.get(position);
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
