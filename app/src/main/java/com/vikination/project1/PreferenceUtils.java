package com.vikination.project1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Viki Andrianto on 9/2/17.
 */

public class PreferenceUtils {

    private static final String CURRENT_MOVIE_LOAD = "current-movie-load";
    private static final String STATE_SAVED_FILE = "state-saved-file";
    private static final String CURRENT_MAIN_SCROLL_POSTION = "current-main-scroll-positon";



    public static void saveCurrentMovieLoad(Context context, String movieTitle){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CURRENT_MOVIE_LOAD, movieTitle);
        editor.commit();
        editor.apply();
    }

    public static String currentMovieLoad(Context context, String defaultValue){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(CURRENT_MOVIE_LOAD, defaultValue);
    }

    public static void saveScrollPositionDetail(Context context, int scrollPosition){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
//        editor.put
        editor.putInt(CURRENT_MAIN_SCROLL_POSTION, scrollPosition);
        editor.commit();
        editor.apply();
    }

    public static int currentDetailScrollPosition(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(CURRENT_MAIN_SCROLL_POSTION, 0);
    }
}
