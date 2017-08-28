package com.alexvit.android.popularmovies.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aleksandrs Vitjukovs on 8/28/2017.
 */

public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity {

    private V viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
