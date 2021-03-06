package com.vikination.project1;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.vikination.project1.Models.PopularDataResponse;
import com.vikination.project1.Models.VolleySingleton;
import com.vikination.project1.data.FavContract;

import org.json.JSONObject;

/**
 * Created by Viki Andrianto on 7/8/17.
 */

class MainPresenterImpl implements MainPresenter{

    private final MainActivityView view;

    public MainPresenterImpl(MainActivityView view){
        this.view = view;
    }

    public void loadMoviewPoster(){
        if (NetworkUtils.isOnline(view.getContextView())){

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                    , NetworkUtils.getUrlMoviePopular(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
//                    Log.i(Utils.TAG, "onResponse: "+response.toString());
                    view.loadStop();
                    PopularDataResponse responseData = new Gson().fromJson(response.toString()
                            , PopularDataResponse.class);
                    view.showResponseMovies(responseData);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(Utils.TAG, "onResponse error: "+error.toString());
                    view.showToast(error.getMessage());
                    view.loadStop();
                }
            });

            VolleySingleton.getInstance(view.getContextView()).addToRequestQueue(jsonObjectRequest);
        }else {
            view.showToast("Network is not available");
        }
    }

    public void loadTopMoviePoster(){
        if (NetworkUtils.isOnline(view.getContextView())){

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                    , NetworkUtils.getUrlMovieTopRated(), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i(Utils.TAG, "onResponse: "+response.toString());
                    view.loadStop();
                    PopularDataResponse responseData = new Gson().fromJson(response.toString()
                            , PopularDataResponse.class);
                    view.showResponseMovies(responseData);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(Utils.TAG, "onResponse error: "+error.toString());
                    view.showToast(error.getMessage());
                    view.loadStop();
                }
            });

            VolleySingleton.getInstance(view.getContextView()).addToRequestQueue(jsonObjectRequest);
        }else {
            view.showToast("Network is not available");
        }
    }

    public void loadFavMoviePoster(){

//        Cursor cursor = view.getContextView().getContentResolver().query(FavContract.FavEntry.CONTENT_URI
//                ,null,null,null,FavContract.FavEntry._ID);
//
//        if (cursor != null){
//            while (cursor.moveToNext()){
//                String data = cursor.getString()
//            }
//        }else {
//            view.showToast("cursor is empty");
//        }


    }

}
