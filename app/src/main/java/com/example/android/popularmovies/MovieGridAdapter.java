package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.models.Movie;

import static com.example.android.popularmovies.utils.ImageUtils.loadUrlIntoImageView;

/**
 * Created by Aleksandrs Vitjukovs on 5/31/2017.
 */

class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridAdapterViewHolder> {

    private final MovieClickListener mMovieClickListener;
    private Movie[] movie;

    public MovieGridAdapter(MovieClickListener mMovieClickListener) {
        this.mMovieClickListener = mMovieClickListener;
    }

    void setMovie(Movie[] movie) {
        this.movie = movie;
        notifyDataSetChanged();
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
        String posterUrl = movie[position].getPosterUrl();
        loadUrlIntoImageView(holder.mPosterImageView.getContext(), posterUrl, R.drawable.placeholder, holder.mPosterImageView);
    }

    @Override
    public int getItemCount() {
        return (movie == null) ? 0 : movie.length;
    }

    Movie getData(int position) {
        return movie[position];
    }

    public interface MovieClickListener {
        void onMovieClicked(Movie movie);
    }

    class MovieGridAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mPosterImageView;

        MovieGridAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = (ImageView) itemView.findViewById(R.id.poster_image);
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
