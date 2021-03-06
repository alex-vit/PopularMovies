package com.alexvit.android.popularmovies.data.models.api;

import com.alexvit.android.popularmovies.data.models.Review;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 7/18/2017.
 */


public class ReviewListResponse {

    @SerializedName("id")
    @Expose
    public Long id;

    @SerializedName("page")
    @Expose
    public Integer page;

    @SerializedName("results")
    @Expose
    public List<Review> reviews = null;

    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;

    @SerializedName("total_results")
    @Expose
    public Integer totalResults;

}