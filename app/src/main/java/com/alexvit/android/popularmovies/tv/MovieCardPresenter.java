package com.alexvit.android.popularmovies.tv;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import com.alexvit.android.popularmovies.R;
import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.utils.Movies;
import com.bumptech.glide.Glide;

/**
 * Created by alexander.vitjukov on 08.09.2017.
 */

class MovieCardPresenter extends Presenter {

    private int defaultBackgroundColor;
    private int selectedBackgroundColor;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        setupColors(parent.getContext());

        ImageCardView cardView = new ImageCardView(parent.getContext()) {
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this, selected);
                super.setSelected(selected);
            }
        };
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;

        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setTitleText(movie.title);
        cardView.setContentText(movie.overview);

        cardView.setMainImageDimensions(
                cardView.getResources().getDimensionPixelSize(R.dimen.tv_card_width),
                cardView.getResources().getDimensionPixelSize(R.dimen.tv_card_height)
        );
        Glide.with(cardView.getContext())
                .load(Movies.fullImageUrl(movie.backdropPath))
                .into(cardView.getMainImageView());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }

    private void setupColors(Context context) {
        defaultBackgroundColor = ContextCompat.getColor(context, R.color.primary_light);
        selectedBackgroundColor = ContextCompat.getColor(context, R.color.accent);
    }

    private void updateCardBackgroundColor(ImageCardView view, boolean selected) {
        int color = selected ? selectedBackgroundColor : defaultBackgroundColor;
        view.setBackgroundColor(color);
    }
}
