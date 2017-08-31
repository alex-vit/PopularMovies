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

    public void onDestroy() {
        compositeSub.clear();
        compositeSub.dispose();
    }

    protected final N getNavigator() {
        return navigator;
    }

    public final void setNavigator(N navigator) {
        this.navigator = navigator;
    }

    protected final <T> void subscribe(Observable<T> observable,
                                       Consumer<? super T> onNext,
                                       Consumer<? super Throwable> onError) {

        Disposable subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);

        compositeSub.add(subscription);
    }
}
