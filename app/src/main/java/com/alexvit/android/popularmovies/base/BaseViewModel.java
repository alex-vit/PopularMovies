package com.alexvit.android.popularmovies.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

public abstract class BaseViewModel<N> {

    private final CompositeDisposable compositeSub = new CompositeDisposable();
    private N navigator;

    public abstract void onViewInitialized();

    protected N getNavigator() {
        return navigator;
    }

    public void setNavigator(N navigator) {
        this.navigator = navigator;
    }

    protected <T> void subscribe(Observable<T> observable,
                                 Consumer<? super T> onNext,
                                 Consumer<? super Throwable> onError) {

        Disposable subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);

        compositeSub.add(subscription);
    }

    public void onDestroy() {
        compositeSub.clear();
        compositeSub.dispose();
    }
}
