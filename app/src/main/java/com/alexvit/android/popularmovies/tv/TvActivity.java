package com.alexvit.android.popularmovies.tv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alexvit.android.popularmovies.R;

/**
 * Created by alexander.vitjukov on 08.09.2017.
 */

public class TvActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
    }
}
