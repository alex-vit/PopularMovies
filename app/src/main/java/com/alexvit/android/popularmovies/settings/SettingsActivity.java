package com.alexvit.android.popularmovies.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alexvit.android.popularmovies.R;

/**
 * Created by Aleksandrs Vitjukovs on 6/3/2017.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.review_content, new SettingsFragment())
                .commit();
    }

}
