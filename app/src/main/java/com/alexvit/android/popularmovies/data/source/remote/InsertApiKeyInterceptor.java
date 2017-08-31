package com.alexvit.android.popularmovies.data.source.remote;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by Aleksandrs Vitjukovs on 8/27/2017.
 */ // https://futurestud.io/tutorials/retrofit-2-how-to-add-query-parameters-to-every-request
public class InsertApiKeyInterceptor implements Interceptor {

    private final String apiKey;

    public InsertApiKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalUrl = originalRequest.url();

        HttpUrl url = originalUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = originalRequest.newBuilder().url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
