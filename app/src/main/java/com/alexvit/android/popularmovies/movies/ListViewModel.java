package com.alexvit.android.popularmovies.movies;

import android.content.Context;

import com.alexvit.android.popularmovies.base.BaseViewModel;
import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.utils.Prefs;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

public class ListViewModel extends BaseViewModel<ListNavigator> {

    private final MoviesRepository moviesRepository;

    private ListNavigator navigator;

    public ListViewModel(MoviesRepository moviesRepository) {
        super();
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void setNavigator(ListNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void onViewInitialized() {
        final String category = Prefs.getCategory((Context) navigator);
    }

    public void onCategoryChanged(String category) {
        Disposable sub = moviesRepository.moviesByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(navigator::displayMovies);
        getCompositeSub().add(sub);
    }
}
