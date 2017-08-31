package com.vikination.project1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

/**
 * Created by Viki Andrianto on 7/8/17.
 */

class NetworkUtils {

    private static final String MOVIE_DB_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_POPULAR = "popular";
    private static final String MOVIE_TOP_RATED = "top_rated";
    private static final String MOVIE_VIDEO = "videos";
    private static final String MOVIE_REVIEWS = "reviews";

    // TODO : Add your APIKEY here
    private static final String API_KEY = "06d7f99d5aa3b1f85f3910c0b52b8e5f";
    private static final String IMAGE_TYPE = "w342";
    public static final String MOVIE_DB_IMG_URL = "https://image.tmdb.org/t/p/"+IMAGE_TYPE+"/";

    public static String getUrlMoviePopular(){
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(MOVIE_POPULAR)
                .appendQueryParameter("api_key",API_KEY)
                .build();
        return builtUri.toString();
    }

    public static String getUrlMovieTopRated(){
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(MOVIE_TOP_RATED)
                .appendQueryParameter("api_key",API_KEY)
                .build();
        return builtUri.toString();
    }

    public static String getMovieVideo(String id) {
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(id)
                .appendPath(MOVIE_VIDEO)
                .appendQueryParameter("api_key", API_KEY)
                .build();
        return builtUri.toString();
    }

    public static String getReviewsMoview(String id){
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(id)
                .appendPath(MOVIE_REVIEWS)
                .appendQueryParameter("api_key", API_KEY)
                .build();
        return builtUri.toString();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
