package com.example.android.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aleksandrs Vitjukovs on 5/31/2017.
 */

public class Movie implements Parcelable {
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private String posterUrl;

    public Movie() {
    }

    public Movie(String posterUrl) {
        setPosterUrl(posterUrl);
    }

    // Parcelable implementation
    protected Movie(Parcel in) {
        posterUrl = in.readString();
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterUrl);
    }
}
