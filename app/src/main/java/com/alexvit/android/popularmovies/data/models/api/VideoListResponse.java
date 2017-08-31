package com.alexvit.android.popularmovies.data.models.api;

/**
 * Created by Aleksandrs Vitjukovs on 7/19/2017.
 */

import com.alexvit.android.popularmovies.data.models.Video;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoListResponse {

    @SerializedName("id")
    @Expose
    public Long id;

    @SerializedName("results")
    @Expose
    public List<Video> videos = null;

}