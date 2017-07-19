package com.example.android.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.data.MovieContract;
import com.example.android.popularmovies.data.ReviewApiLoader;
import com.example.android.popularmovies.databinding.ActivityDetailsBinding;
import com.example.android.popularmovies.models.Movie;
import com.example.android.popularmovies.models.Review;
import com.example.android.popularmovies.util.Api;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.DecimalFormat;
import java.util.List;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Review>> {

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final int REVIEW_LOADER_ID = 1200;

    private ActivityDetailsBinding binding;
    private ReviewAdapter mReviewAdapter;

    private int mMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        mReviewAdapter = new ReviewAdapter(binding.reviewList);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout mCollapsingToolbarLayout = binding.collapsingToolbar;
        mCollapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary_dark));
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white_text));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white_text));

        Movie mMovie = null;

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.e(TAG, "No extras were passed.");
        } else {
            mMovie = getIntent().getParcelableExtra("movie");
            mMovieId = mMovie.id;

            // TODO: Calculate best resolution based on size / column count
            String posterSize = Api.PosterSize.w185;
            String backdropSize;
            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_LANDSCAPE) {
                backdropSize = Api.BackdropSize.w780;
            } else {
                backdropSize = Api.BackdropSize.w300;
            }

            mCollapsingToolbarLayout.setTitle(mMovie.title);

            String posterUrl = Api.fullImageUrl(mMovie.posterPath, posterSize);
            Glide.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(binding.ivPoster);

            String backdropUrl = Api.fullImageUrl(mMovie.backdropPath, backdropSize);
            Glide.with(this)
                    .load(backdropUrl)
                    .placeholder(R.drawable.placeholder_backdrop)
                    .into(binding.ivBackdrop);

            binding.tvYear.setText(mMovie.year());
            binding.tvVotes.setText(
                    "Rating: "
                            + new DecimalFormat("#0.0").format(mMovie.voteAverage)
                            + " (" + mMovie.voteCount.toString() + " votes)"
            );
            binding.tvOverview.setText(mMovie.overview);

            Uri uri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, mMovie.id);
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            boolean isFavorite = false;
            if (cursor != null && cursor.moveToFirst()) {
                isFavorite = (cursor.getInt(cursor.getColumnIndex(
                        MovieContract.MovieEntry.COLUMN_IS_FAVORITE)) == 1);
            }
            binding.toggleFavortie.setChecked(isFavorite);
            binding.toggleFavortie.setOnCheckedChangeListener(new FavoriteToggleListener(mMovie));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle args = new Bundle();
        args.putInt("movieId", mMovieId);
        getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, args, this);
    }

    @Override
    public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case REVIEW_LOADER_ID:
                int movieId = args.getInt("movieId");
                return new ReviewApiLoader(this, movieId);
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviews) {
        mReviewAdapter.setReviews(reviews);
    }

    @Override
    public void onLoaderReset(Loader<List<Review>> loader) {
        mReviewAdapter.deleteReviews();
    }

    private class FavoriteToggleListener implements CompoundButton.OnCheckedChangeListener {

        private Movie movie;

        FavoriteToggleListener(Movie movie) {
            this.movie = movie;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                movie.isFavorite = true;
                ContentValues values = Api.movieToContentValues(movie);
                getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
            } else {
                getContentResolver().delete(
                        ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, movie.id),
                        null,
                        null);
            }
            getContentResolver().notifyChange(MovieContract.MovieEntry.CONTENT_URI, null);
        }
    }

    private class ReviewAdapter {

        private LinearLayout mParent;

        ReviewAdapter(LinearLayout parent) {
            this.mParent = parent;
        }

        public void setReviews(List<Review> reviewList) {
            mParent.removeAllViews();

            if (reviewList == null || reviewList.size() == 0) return;

            for (Review review: reviewList) {
                addReview(review);
            }
        }

        public void deleteReviews() {
            setReviews(null);
        }

        private void addReview(Review review) {
            LayoutInflater inflater = LayoutInflater.from(mParent.getContext());
            View itemView = inflater.inflate(R.layout.review_item, null, false);
            ((TextView) itemView.findViewById(R.id.review_author)).setText(review.author);
            ((TextView) itemView.findViewById(R.id.review_content)).setText(review.content);
            mParent.addView(itemView);
        }

    }
}
