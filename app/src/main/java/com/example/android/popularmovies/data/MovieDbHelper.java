package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by Aleksandrs Vitjukovs on 7/15/2017.
 */

class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 2;

    MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE IF NOT EXISTS " + MovieEntry.TABLE_NAME + " ("
                + MovieEntry._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, "
                + MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, "
                + MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, "
                + MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, "
                + MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, "
                + MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, "
                + MovieEntry.COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Review suggested not using drop table in production.
        // So I'm just removing this until I have an actual migration to worry about.
//        final String SQL_DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
//        onCreate(db);
    }
}
