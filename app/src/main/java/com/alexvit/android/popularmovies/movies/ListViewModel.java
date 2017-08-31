package com.alexvit.android.popularmovies.movies;

import com.alexvit.android.popularmovies.base.BaseViewModel;
import com.alexvit.android.popularmovies.data.MoviesRepository;

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
        subscribe(moviesRepository.moviesByCategory(category),
                getNavigator()::onMoviesLoaded,
                getNavigator()::onError);
    }
}
