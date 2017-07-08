package com.vikination.project1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Viki Andrianto on 7/8/17.
 */

class NetworkUtils {

    private static final String MOVIE_DB_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_POPULAR = "popular";
    private static final String MOVIE_TOP_RATED = "top_rated";

    // TODO : Add your APIKEY here
    private static final String API_KEY = "";
    private static final String IMAGE_TYPE = "w342";
    public static final String MOVIE_DB_IMG_URL = "https://image.tmdb.org/t/p/"+IMAGE_TYPE+"/";



    public static URL buildMoviewPopularUrl(){
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(MOVIE_POPULAR)
                .appendQueryParameter("api_key",API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildTopRatedMovieUrl(){
        Uri builtUri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(MOVIE_TOP_RATED)
                .appendQueryParameter("api_key",API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
