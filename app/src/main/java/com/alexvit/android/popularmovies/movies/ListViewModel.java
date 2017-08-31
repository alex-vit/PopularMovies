package com.alexvit.android.popularmovies.movies;

import com.alexvit.android.popularmovies.base.BaseViewModel;
import com.alexvit.android.popularmovies.data.MoviesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    public void onCategoryChanged(String category) {
        Disposable sub = moviesRepository.moviesByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getNavigator()::onMoviesLoaded);
        getCompositeSub().add(sub);
    }
}
