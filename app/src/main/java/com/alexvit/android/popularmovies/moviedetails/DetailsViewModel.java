package com.alexvit.android.popularmovies.moviedetails;

import com.alexvit.android.popularmovies.base.BaseViewModel;
import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.data.models.Movie;

/**
 * Created by Aleksandrs Vitjukovs on 8/31/2017.
 */

public class DetailsViewModel extends BaseViewModel<DetailsNavigator> {

    private final MoviesRepository moviesRepository;

    public DetailsViewModel(MoviesRepository moviesRepository) {
        super();
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void onViewInitialized() {

    }

    void onMovieId(long movieId) {

        subscribe(moviesRepository.movieById(movieId),
                getNavigator()::onMovieLoaded,
                getNavigator()::onError);

        subscribe(moviesRepository.reviewsByMovieId(movieId),
                getNavigator()::onReviewsLoaded,
                getNavigator()::onError);

        subscribe(moviesRepository.videosByMovieId(movieId),
                getNavigator()::onVideosLoaded,
                getNavigator()::onError);
    }

    void onFavorite(Movie movie, boolean isFavorite) {
        movie.favorite = isFavorite;
        moviesRepository.updateMovie(movie);
    }
}
