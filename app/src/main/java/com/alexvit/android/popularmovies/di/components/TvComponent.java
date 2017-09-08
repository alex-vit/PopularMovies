package com.alexvit.android.popularmovies.di.components;

import com.alexvit.android.popularmovies.di.modules.TvFragmentModule;
import com.alexvit.android.popularmovies.di.scopes.TvFragmentScope;
import com.alexvit.android.popularmovies.tv.TvFragment;

import dagger.Component;

/**
 * Created by alexander.vitjukov on 08.09.2017.
 */

@TvFragmentScope
@Component(modules = {TvFragmentModule.class}, dependencies = {AppComponent.class})
public interface TvComponent {
    void inject(TvFragment tvFragment);
}
