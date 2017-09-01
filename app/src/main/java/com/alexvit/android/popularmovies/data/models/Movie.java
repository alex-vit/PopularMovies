package com.alexvit.android.popularmovies.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Aleksandrs Vitjukovs on 8/31/2017.
 */

@Entity(tableName = "movies",
        indices = {@Index("popularity"), @Index("vote_average")})
public class Movie {

    @Expose
    @SerializedName("id")
    @PrimaryKey
    public Long id;

    @Expose
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    public String posterPath;

    @SerializedName("adult")
    @Expose
    public Boolean adult;

    @SerializedName("overview")
    @Expose
    public String overview;

    @SerializedName("release_date")
    @Expose
    @ColumnInfo(name = "release_date")
    public String releaseDate;

    @SerializedName("original_title")
    @Expose
    @ColumnInfo(name = "original_title")
    public String originalTitle;

    @SerializedName("original_language")
    @Expose
    @ColumnInfo(name = "original_language")
    public String originalLanguage;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("backdrop_path")
    @Expose
    @ColumnInfo(name = "backdrop_path")
    public String backdropPath;

    @SerializedName("popularity")
    @Expose
    public Double popularity;

    @SerializedName("vote_count")
    @Expose
    @ColumnInfo(name = "vote_count")
    public Integer voteCount;

    @SerializedName("video")
    @Expose
    public Boolean video;

    @SerializedName("vote_average")
    @Expose
    @ColumnInfo(name = "vote_average")
    public Double voteAverage;

    /*
     * API-only fields
     */

    @SerializedName("genre_ids")
    @Expose
    @Ignore
    public List<Integer> genreIds = null;

    /*
     * DB-only fields
     */

    public Boolean favorite;


}
