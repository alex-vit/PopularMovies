package com.example.android.popularmovies.models;

/**
 * Created by Aleksandrs Vitjukovs on 7/19/2017.
 */

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoListResponse {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("results")
    @Expose
    public List<Video> videos = null;

}