package com.alexvit.android.popularmovies.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alexvit.android.popularmovies.App;
import com.alexvit.android.popularmovies.di.ActivityComponent;
import com.alexvit.android.popularmovies.di.ActivityModule;
import com.alexvit.android.popularmovies.di.DaggerActivityComponent;

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

    protected ActivityComponent getComponent() {
        return component;
    }
}
