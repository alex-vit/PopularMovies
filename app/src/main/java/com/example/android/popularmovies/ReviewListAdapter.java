package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.models.Review;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 7/18/2017.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewListAdapterViewHolder> {

    private static final int TRIM = 140;

    private List<Review> reviewList = null;

    @Override
    public ReviewListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutId, parent, false);
        return new ReviewListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewListAdapterViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.mTvAuthor.setText(review.author);
//        String trimmedReview = review.content.substring(0, Math.min(review.content.length(), TRIM))
//                + "...";
        // TODO: Expand on click.
        holder.mTvContent.setText(review.content);
    }

    @Override
    public int getItemCount() {
        return (reviewList == null) ? 0 : reviewList.size();
    }

    void setReviews(List<Review> reviews) {
        this.reviewList = reviews;
        notifyDataSetChanged();
    }

    void deleteReviews() {
        setReviews(null);
    }

    class ReviewListAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView mTvAuthor;
        final TextView mTvContent;

        ReviewListAdapterViewHolder(View itemView) {
            super(itemView);
            mTvAuthor = (TextView) itemView.findViewById(R.id.review_author);
            mTvContent = (TextView) itemView.findViewById(R.id.review_content);
        }

    }

}
