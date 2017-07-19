package com.example.android.popularmovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.models.MovieExtras;
import com.example.android.popularmovies.models.Review;
import com.example.android.popularmovies.models.Video;

/**
 * Created by Aleksandrs Vitjukovs on 7/20/2017.
 */

class MovieExtrasAdapter {

    private final ViewGroup reviewParent;
    private final ViewGroup videoParent;

    MovieExtrasAdapter(ViewGroup reviewParent, ViewGroup videoParent) {
        this.reviewParent = reviewParent;
        this.videoParent = videoParent;
    }

    void setExtras(MovieExtras extras) {
        reviewParent.removeAllViews();
        videoParent.removeAllViews();
        if (extras == null) return;

        if (extras.reviews != null && extras.reviews.size() > 0) {
            for (Review review : extras.reviews) {
                addReview(review);
            }
        }

        if (extras.videos != null && extras.videos.size() > 0) {
            for (Video video : extras.videos) {
                addVideo(video);
            }
        }
    }

    void deleteExtras() {
        setExtras(null);
    }

    private void addReview(Review review) {
        LayoutInflater inflater = LayoutInflater.from(reviewParent.getContext());
        View itemView = inflater.inflate(R.layout.review_item, reviewParent, false);
        ((TextView) itemView.findViewById(R.id.review_author)).setText(review.author);
        ((TextView) itemView.findViewById(R.id.review_content)).setText(review.content);
        reviewParent.addView(itemView);
    }

    private void addVideo(Video video) {
        LayoutInflater inflater = LayoutInflater.from(videoParent.getContext());
        View itemView = inflater.inflate(R.layout.video_item, videoParent, false);
        ((TextView) itemView.findViewById(R.id.video_name)).setText(video.name);
        videoParent.addView(itemView);
    }
}
