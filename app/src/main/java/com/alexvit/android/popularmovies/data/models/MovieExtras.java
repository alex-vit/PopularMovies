package com.alexvit.android.popularmovies.data.models;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 7/19/2017.
 */

public class MovieExtras {
    public List<Review> reviews = null;
    public List<Video> videos = null;

    private MovieExtras() {}

    public MovieExtras(List<Review> reviews, List<Video> videos) {
        this.reviews = reviews;
        this.videos = videos;
    }
}
