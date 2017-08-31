package com.alexvit.android.popularmovies.moviedetails;

import com.alexvit.android.popularmovies.base.BaseViewModel;
import com.alexvit.android.popularmovies.data.MoviesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Aleksandrs Vitjukovs on 8/31/2017.
 */

public class DetailsViewModel extends BaseViewModel<DetailsNavigator> {

    private final MoviesRepository moviesRepository;

    private DetailsNavigator navigator;

    public DetailsViewModel(MoviesRepository moviesRepository) {
        super();
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void setNavigator(DetailsNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void onViewInitialized() {

    }

    void onMovieId(long movieId) {
        Disposable moviesSub = moviesRepository.movieById(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(navigator::onMovieLoaded);
        getCompositeSub().add(moviesSub);

        Disposable reviewsSub = moviesRepository.reviewsByMovieId(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(navigator::onReviewsLoaded);
        getCompositeSub().add(reviewsSub);

        Disposable videosSub = moviesRepository.videosByMovieId(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(navigator::onVideosLoaded);
        getCompositeSub().add(videosSub);
    }
}
