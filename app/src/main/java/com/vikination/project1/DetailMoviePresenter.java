package com.vikination.project1;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.vikination.project1.Models.ReviewsDataResponse;
import com.vikination.project1.Models.TrailerDataResponse;
import com.vikination.project1.Models.VolleySingleton;

import org.json.JSONObject;

/**
 * Created by Viki Andrianto on 8/8/17.
 */

public class DetailMoviePresenter {
    DetailMovieView view;

    public DetailMoviePresenter(DetailMovieView view){
        this.view = view;
    }

    public void loadMovieVideo(String movieId){
        if (NetworkUtils.isOnline(view.getContextView())){
            view.loadStart();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                    , NetworkUtils.getMovieVideo(movieId), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    view.loadStop();
                    TrailerDataResponse trailerDataResponse = new Gson().fromJson(response.toString()
                            , TrailerDataResponse.class);
                    view.trailerResult(trailerDataResponse.getResults());
                    Log.i(Utils.TAG, "onResponse movie video: "+new Gson().toJson(trailerDataResponse));
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

    public void loadReviewsVideo(String movieId){
        if (NetworkUtils.isOnline(view.getContextView())){
            view.loadStart();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                    , NetworkUtils.getReviewsMoview(movieId), null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    view.loadStop();
                    ReviewsDataResponse reviewsDataResponse = new Gson().fromJson(response.toString()
                            ,ReviewsDataResponse.class);
                    view.reviewsResult(reviewsDataResponse.getResults());
//                    TrailerDataResponse trailerDataResponse = new Gson().fromJson(response.toString()
//                            , TrailerDataResponse.class);
//                    view.trailerResult(trailerDataResponse.getResults());
                    Log.i(Utils.TAG, "onResponse review video: "+response.toString());
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
}
