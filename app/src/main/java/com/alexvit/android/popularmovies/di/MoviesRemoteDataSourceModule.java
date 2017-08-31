package com.alexvit.android.popularmovies.di;

import android.content.Context;

import com.alexvit.android.popularmovies.BuildConfig;
import com.alexvit.android.popularmovies.data.source.remote.InsertApiKeyInterceptor;
import com.alexvit.android.popularmovies.data.source.remote.MoviesRemoteDataSource;
import com.alexvit.android.popularmovies.data.source.remote.TheMovieDbService;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */

@Module(includes = {ContextModule.class})
public class MoviesRemoteDataSourceModule {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    @Provides
    @ApplicationScope
    MoviesRemoteDataSource moviesRemoteDataSource(TheMovieDbService service) {
        return new MoviesRemoteDataSource(service);
    }

    @Provides
    @ApplicationScope
    TheMovieDbService theMovieDbService(Retrofit tmdbRetrofit) {
        return tmdbRetrofit.create(TheMovieDbService.class);
    }

    @Provides
    @ApplicationScope
    Retrofit retrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    OkHttpClient okHttpClient(InsertApiKeyInterceptor insertApiKeyInterceptor, Cache cache) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(insertApiKeyInterceptor).cache(cache);
        return builder.build();
    }

    @Provides
    @ApplicationScope
    InsertApiKeyInterceptor insertApiKeyInterceptor(@ApiKey String apiKey) {
        return new InsertApiKeyInterceptor(apiKey);
    }

    @Provides
    @ApplicationScope
    Cache cache(@CacheFile File cacheFile) {
        return new Cache(cacheFile, 10 * 1024 * 1024);
    }

    @Provides
    @ApplicationScope
    @CacheFile
    File cacheFile(@ApplicationContext Context context) {
        return new File(context.getCacheDir(), "okhttp-cache");
    }

    @Provides
    @ApplicationScope
    @ApiKey
    String apiKey() {
        return BuildConfig.TMDB_V3_API_KEY;
    }
}
