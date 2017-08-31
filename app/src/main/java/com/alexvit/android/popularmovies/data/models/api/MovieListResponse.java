package com.alexvit.android.popularmovies.data.models.api;

/**
 * Created by Aleksandrs Vitjukovs on 6/2/2017.
 *
 * GSON and Parcelable implementation generated using
 * - http://www.parcelabler.com/
 * - http://www.jsonschema2pojo.org/
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.alexvit.android.popularmovies.data.models.Movie;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieListResponse implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieListResponse> CREATOR = new Parcelable.Creator<MovieListResponse>() {
        @Override
        public MovieListResponse createFromParcel(Parcel in) {
            return new MovieListResponse(in);
        }

        @Override
        public MovieListResponse[] newArray(int size) {
            return new MovieListResponse[size];
        }
    };
    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("results")
    @Expose
    public List<Movie> movies = null;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;

    protected MovieListResponse(Parcel in) {
        page = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            movies = new ArrayList<>();
            in.readList(movies, Movie.class.getClassLoader());
        } else {
            movies = null;
        }
        totalResults = in.readByte() == 0x00 ? null : in.readInt();
        totalPages = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (page == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(page);
        }
        if (movies == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(movies);
        }
        if (totalResults == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalResults);
        }
        if (totalPages == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalPages);
        }
    }
}