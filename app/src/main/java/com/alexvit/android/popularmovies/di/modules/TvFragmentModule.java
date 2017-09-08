package com.alexvit.android.popularmovies.di.modules;

import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRowPresenter;

import com.alexvit.android.popularmovies.di.scopes.TvFragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alexander.vitjukov on 08.09.2017.
 */

@Module
public class TvFragmentModule {
    @Provides
    @TvFragmentScope
    ArrayObjectAdapter arrayObjectAdapter(ListRowPresenter listRowPresenter) {
        return new ArrayObjectAdapter(listRowPresenter);
    }

    @Provides
    @TvFragmentScope
    ListRowPresenter listRowPresenter() {
        return new ListRowPresenter();
    }
}
