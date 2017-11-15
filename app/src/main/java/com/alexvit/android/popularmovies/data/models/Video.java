package com.alexvit.android.popularmovies.data.models;

/**
 * Created by Aleksandrs Vitjukovs on 7/19/2017.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "videos",
        indices = {@Index("movie_id")},
        foreignKeys = @ForeignKey(
                entity = Movie.class,
                parentColumns = "id",
                childColumns = "movie_id",
                onDelete = CASCADE,
                onUpdate = CASCADE
        ))
public class Video {

    @Expose
    @SerializedName("id")
    @PrimaryKey
    @NonNull
    public String id = "";

    @ColumnInfo(name = "movie_id")
    public Long movieId;

    @Expose
    @SerializedName("iso_639_1")
    @ColumnInfo(name = "iso_639_1")
    public String iso6391;

    @Expose
    @SerializedName("iso_3166_1")
    @ColumnInfo(name = "iso_3166_1")
    public String iso31661;

    @Expose
    @SerializedName("key")
    public String key;

    @Expose
    @SerializedName("name")
    public String name;

    @Expose
    @SerializedName("site")
    public String site;

    @Expose
    @SerializedName("size")
    public Integer size;

    @Expose
    @SerializedName("type")
    public String type;
}
