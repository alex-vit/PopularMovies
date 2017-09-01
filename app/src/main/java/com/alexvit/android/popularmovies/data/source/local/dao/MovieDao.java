package com.alexvit.android.popularmovies.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.alexvit.android.popularmovies.data.models.Movie;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Aleksandrs Vitjukovs on 8/31/2017.
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies WHERE id = :id")
    Single<Movie> movieById(long id);

    @Query("SELECT * FROM movies WHERE favorite = 1")
    Flowable<List<Movie>> moviesByFavorite();

    @Query("SELECT * FROM movies ORDER BY popularity DESC LIMIT 20")
    Flowable<List<Movie>> moviesByPopularity();

    @Query("SELECT * FROM movies ORDER BY vote_average DESC LIMIT 20")
    Flowable<List<Movie>> moviesByRating();

//    @Query("SELECT * FROM movies WHERE id IN (:listOfIds)")
//    public List<Movie> moviesByIdList(List<Long> listOfIds);

    @Insert(onConflict = REPLACE)
    long insert(Movie movie);

    @Insert(onConflict = REPLACE)
    long[] insert(List<Movie> movie);

    @Update(onConflict = REPLACE)
    int update(Movie movie);
}
