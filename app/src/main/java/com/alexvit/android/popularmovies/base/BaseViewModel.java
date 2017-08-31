package com.alexvit.android.popularmovies.base;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

public abstract class BaseViewModel<N> {

    private N navigator;

    private final CompositeDisposable compositeSub = new CompositeDisposable();

    protected CompositeDisposable getCompositeSub() {
        return compositeSub;
    }

    protected N getNavigator() {
        return navigator;
    }

    public void setNavigator(N navigator) {
        this.navigator = navigator;
    }

    public abstract void onViewInitialized();

    public void onDestroy() {
        compositeSub.clear();
        compositeSub.dispose();
    }
}
