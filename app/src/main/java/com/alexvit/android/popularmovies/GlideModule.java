package com.alexvit.android.popularmovies;

import android.content.Context;

import com.bumptech.glide.Glide;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

@Module(includes = {ContextModule.class})
public class GlideModule {

    @Provides
    public Glide glide(Context context) {
        return Glide.get(context);
    }
}
