package com.example.android.popularmovies.models;

/**
 * Created by Aleksandrs Vitjukovs on 6/2/2017.
 *
 * GSON and Parcelable implementation generated using
 * - http://www.parcelabler.com/
 * - http://www.jsonschema2pojo.org/
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListResponse implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ListResponse> CREATOR = new Parcelable.Creator<ListResponse>() {
        @Override
        public ListResponse createFromParcel(Parcel in) {
            return new ListResponse(in);
        }

        @Override
        public ListResponse[] newArray(int size) {
            return new ListResponse[size];
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

    protected ListResponse(Parcel in) {
        page = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            movies = new ArrayList<Movie>();
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