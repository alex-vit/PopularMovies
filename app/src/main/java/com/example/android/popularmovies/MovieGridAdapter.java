package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.models.MovieData;
import com.squareup.picasso.Picasso;

/**
 * Created by alex on 5/31/2017.
 */

class MovieGridAdapter extends RecyclerView.Adapter<MovieGridAdapter.MovieGridAdapterViewHolder> {

    private MovieData[] movieData;

    void setMovieData(MovieData[] movieData) {
        this.movieData = movieData;
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
        String posterUrl = movieData[position].getPosterUrl();
        Picasso
                .with(holder.mPosterImageView.getContext())
                .load(posterUrl)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.mPosterImageView);
    }

    @Override
    public int getItemCount() {
        return (movieData == null) ? 0 : movieData.length;
    }

    class MovieGridAdapterViewHolder extends RecyclerView.ViewHolder {
        final ImageView mPosterImageView;

        MovieGridAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = (ImageView) itemView.findViewById(R.id.poster_image);
        }
    }
}
