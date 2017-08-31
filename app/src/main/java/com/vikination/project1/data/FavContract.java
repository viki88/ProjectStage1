package com.vikination.project1.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Viki Andrianto on 8/8/17.
 */

public final class FavContract {

    public static final String AUTHORITY = "com.vikination.project1";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String PATH_FAV = "fav";

    private FavContract(){}

    public static class FavEntry implements BaseColumns{
        public static Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();

        public static final String TABLE_NAME = "fav";
        public static final String COLLUMN_NAME_ID = "id";
        public static final String COLLUMN_NAME_TITLE = "title";
        public static final String COLLUMN_MOVIE_POSTER_IMAGE = "image_poster";
        public static final String COLLUMN_RELEASE_DATE = "release_date";
        public static final String COLLUMN_RATING = "rating";
        public static final String COLLUMN_REVIEW = "review";
        public static final String COLLUMN_TRAILERS = "trailers";
        public static final String COLLUMN_REVIEWS = "reviews";
        public static final String COLLUMN_IMAGE_BYTE = "image_poster_byte";

    }

}
