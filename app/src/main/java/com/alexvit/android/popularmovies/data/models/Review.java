package com.alexvit.android.popularmovies.data.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Aleksandrs Vitjukovs on 7/18/2017.
 */

@Entity(tableName = "reviews",
        indices = {@Index("movie_id")},
        foreignKeys = @ForeignKey(
                entity = Movie.class,
                parentColumns = "id",
                childColumns = "movie_id",
                onDelete = CASCADE,
                onUpdate = CASCADE
        ))
public class Review {

    @Expose
    @SerializedName("id")
    @PrimaryKey
    public String id;

    @ColumnInfo(name = "movie_id")
    public Long movieId;

    @Expose
    @SerializedName("author")
    public String author;

    @Expose
    @SerializedName("content")
    public String content;

    @Expose
    @SerializedName("url")
    public String url;

}