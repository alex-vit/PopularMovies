package com.alexvit.android.popularmovies.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.data.models.Review;
import com.alexvit.android.popularmovies.data.models.Video;
import com.alexvit.android.popularmovies.data.source.local.dao.MovieDao;

/**
 * Created by Aleksandrs Vitjukovs on 8/31/2017.
 */

@Database(entities = {Movie.class, Review.class, Video.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
//    public abstract ReviewDao reviewDao();
//    public abstract VideoDao videoDao();
}
