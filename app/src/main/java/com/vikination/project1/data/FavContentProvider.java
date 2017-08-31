package com.vikination.project1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Viki Andrianto on 8/9/17.
 */

public class FavContentProvider extends ContentProvider{

    public static final int FAV = 100;
    public static final int FAV_WITH_ID = 101;

    private FavDbHelper dbHelper;
    private final UriMatcher sUrimatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        dbHelper = new FavDbHelper(getContext());
        return true;
    }

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(FavContract.AUTHORITY, FavContract.PATH_FAV, FAV);
        uriMatcher.addURI(FavContract.AUTHORITY, FavContract.PATH_FAV+"/#", FAV_WITH_ID);
        return uriMatcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection
            , @Nullable String selection, @Nullable String[] selectionArgs
            , @Nullable String sortOrder) {

        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUrimatcher.match(uri);

        switch (match){
            case FAV:
                cursor = db.query(FavContract.FavEntry.TABLE_NAME
                        ,projection,selection,null,null,null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : "+uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        int match = sUrimatcher.match(uri);

        Uri returnUri;

        switch (match){
            case FAV:
                long id = db.insert(FavContract.FavEntry.TABLE_NAME
                        ,null
                        ,values);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(FavContract.BASE_CONTENT_URI, id);
                }else {
                    throw new SQLException("failed to insert to database : "+uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("unknown uri : "+uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase database = dbHelper.getWritableDatabase();

        int id;

        int match = sUrimatcher.match(uri);

        switch (match){
            case FAV:
                id = database.delete(FavContract.FavEntry.TABLE_NAME
                        ,selection
                        ,selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("unknown uri : "+uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
