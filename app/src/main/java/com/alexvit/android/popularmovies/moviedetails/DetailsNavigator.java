package com.alexvit.android.popularmovies.moviedetails;

import com.alexvit.android.popularmovies.base.BaseNavigator;
import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.data.models.Review;
import com.alexvit.android.popularmovies.data.models.Video;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 8/31/2017.
 */

interface DetailsNavigator extends BaseNavigator {

    void onMovieLoaded(Movie movie);

    void onReviewsLoaded(List<Review> reviews);

    void onVideosLoaded(List<Video> videos);
}
