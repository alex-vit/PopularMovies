package com.alexvit.android.popularmovies.data.source.remote;

import com.alexvit.android.popularmovies.BuildConfig;
import com.alexvit.android.popularmovies.data.Movie;
import com.alexvit.android.popularmovies.data.Review;
import com.alexvit.android.popularmovies.data.Video;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aleksandrs Vitjukovs on 8/21/2017.
 */

public final class MoviesRemoteDataSource {

    private MoviesRemoteDataSource() {
    }

    private static TheMovieDbService SERVICE = null;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static Observable<List<Movie>> movies(String category) {
        return service().movies(category).map(r -> r.movies);
    }

    public static Observable<List<Review>> reviews(String movieId) {
        return service().reviews(movieId).map(r -> r.reviews);
    }

    public static Observable<List<Video>> videos(String movieId) {
        return service().videos(movieId).map(r -> r.videos);
    }

    private static synchronized TheMovieDbService service() {
        if (SERVICE == null) {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(buildOkHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SERVICE = retrofit.create(TheMovieDbService.class);
        }

        return SERVICE;
    }

    private static OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new InsertApiKeyInterceptor());
        return builder.build();
    }

    private static String apiKey() {
        return BuildConfig.TMDB_V3_API_KEY;
    }

    // https://futurestud.io/tutorials/retrofit-2-how-to-add-query-parameters-to-every-request
    private static class InsertApiKeyInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            HttpUrl originalUrl = originalRequest.url();

            HttpUrl url = originalUrl.newBuilder()
                    .addQueryParameter("api_key", apiKey())
                    .build();

            // Request customization: add request headers
            Request.Builder requestBuilder = originalRequest.newBuilder().url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }

}
