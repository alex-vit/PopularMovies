package com.alexvit.android.popularmovies.movies;

import com.alexvit.android.popularmovies.base.BaseViewModel;
import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.data.models.Movie;

import java.util.List;

import io.reactivex.Observable;

import static com.alexvit.android.popularmovies.utils.Constants.CATEGORY_FAVORITE;
import static com.alexvit.android.popularmovies.utils.Constants.CATEGORY_POPULAR;
import static com.alexvit.android.popularmovies.utils.Constants.CATEGORY_TOP_RATED;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

public class ListViewModel extends BaseViewModel<ListNavigator> {

    private final MoviesRepository moviesRepository;

    public ListViewModel(MoviesRepository moviesRepository) {
        super();
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void onViewInitialized() {

    }

    void onCategoryChanged(String category) {

        getCompositeSub().clear(); // because otherwise "favorites" flowable survives

        Observable<List<Movie>> moviesObs = null;
        switch (category) {
            case CATEGORY_FAVORITE:
                moviesObs = moviesRepository.moviesByFavorite();
                break;
            case CATEGORY_POPULAR:
                moviesObs = moviesRepository.moviesByPopularity();
                break;
            case CATEGORY_TOP_RATED:
                moviesObs = moviesRepository.moviesByRating();
                break;
            default:
                throw new UnsupportedOperationException("Unknown category " + category);
        }

        subscribe(moviesObs,
                getNavigator()::onMoviesLoaded,
                getNavigator()::onError);
    }
}
