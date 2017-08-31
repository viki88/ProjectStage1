package com.vikination.project1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Viki Andrianto on 8/8/17.
 */

public class FavDbHelper extends SQLiteOpenHelper{

    public static String DB_NAME = "favmovies.db";
    public static int DB_VERSION = 1;

    public FavDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAV_TABLE = "CREATE TABLE " +
                FavContract.FavEntry.TABLE_NAME + "(" +
                FavContract.FavEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavContract.FavEntry.COLLUMN_NAME_ID + " INTEGER NOT NULL, " +
                FavContract.FavEntry.COLLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                FavContract.FavEntry.COLLUMN_MOVIE_POSTER_IMAGE + " TEXT NOT NULL, " +
                FavContract.FavEntry.COLLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavContract.FavEntry.COLLUMN_IMAGE_BYTE + " BLOB NOT NULL, " +
                FavContract.FavEntry.COLLUMN_RATING + " TEXT NOT NULL, " +
                FavContract.FavEntry.COLLUMN_REVIEW + " TEXT NOT NULL, " +
                FavContract.FavEntry.COLLUMN_REVIEWS + " TEXT NOT NULL, " +
                FavContract.FavEntry.COLLUMN_TRAILERS + " TEXT NOT NULL" +
                ");";
        db.execSQL(SQL_CREATE_FAV_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + FavContract.FavEntry.TABLE_NAME);
        onCreate(db);
    }
}
