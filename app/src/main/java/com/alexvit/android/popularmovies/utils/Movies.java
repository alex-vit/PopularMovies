package com.alexvit.android.popularmovies.utils;

import android.net.Uri;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 6/2/2017.
 */

public class Movies {

    @SuppressWarnings("unused")
    private static final String TAG = Movies.class.getSimpleName();

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p";
    private static final List<String> SUPPORTED_VIDEO_SITES = Arrays.asList(VideoSite.YouTube);

    public static String fullImageUrl(String imagePath) {
        return fullImageUrl(imagePath, PosterSize.w185);
    }

    public static String fullImageUrl(String imagePath, String size) {
        return Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(size)
                .appendPath(imagePath.substring(1))
                .build()
                .toString();
    }

    public static boolean isSupportedVideoSite(String site) {
        return SUPPORTED_VIDEO_SITES.contains(site);
    }

    public static Uri getVideoUri(String site, String key) {
        switch (site) {
            case VideoSite.YouTube:
                return Uri.parse(VideoSiteUrl.YouTube)
                        .buildUpon()
                        .appendQueryParameter("v", key)
                        .build();
            default:
                throw new UnsupportedOperationException("Unknown video site: " + site);
        }

    }

    public static final class PosterSize {
        public static final String w185 = "w185";
        public static final String w342 = "w342";
    }

    public static final class BackdropSize {
        public static final String w300 = "w300";
        public static final String w780 = "w780";
    }

    public static final class VideoSite {
        public static final String YouTube = "YouTube";
    }

    private static final class VideoSiteUrl {
        public static final String YouTube = "https://www.youtube.com/watch";
    }
}
