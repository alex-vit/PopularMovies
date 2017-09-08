package com.alexvit.android.popularmovies.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BrowseFragment;

import com.alexvit.android.popularmovies.App;
import com.alexvit.android.popularmovies.di.components.DaggerTvComponent;
import com.alexvit.android.popularmovies.di.components.TvComponent;

/**
 * Created by alexander.vitjukov on 08.09.2017.
 */

public abstract class BaseBrowseFragment extends BrowseFragment {

    private TvComponent component;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        component = DaggerTvComponent.builder()
                .appComponent(App.get(getActivity()).component())
                .build();
    }

    protected TvComponent getComponent() {
        return component;
    }
}
