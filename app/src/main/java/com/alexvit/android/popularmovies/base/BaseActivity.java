package com.alexvit.android.popularmovies.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alexvit.android.popularmovies.App;
import com.alexvit.android.popularmovies.di.DaggerActivityComponent;
import com.alexvit.android.popularmovies.di.components.ActivityComponent;
import com.alexvit.android.popularmovies.di.modules.ActivityModule;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity {

    private ActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.get(this).component())
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getViewModel().onDestroy();
    }

    protected abstract V getViewModel();

    protected final ActivityComponent getComponent() {
        return component;
    }
}
