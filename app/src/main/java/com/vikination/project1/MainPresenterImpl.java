package com.vikination.project1;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.vikination.project1.Models.PopularDataResponse;

import java.io.IOException;
import java.net.URL;

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
            new LoadMovieDbNetwork().execute(NetworkUtils.buildMoviewPopularUrl());
        }else {
            view.showToast("Network is not available");
        }
    }

    public void loadTopMoviePoster(){
        if (NetworkUtils.isOnline(view.getContextView())){
            new LoadMovieDbNetwork().execute(NetworkUtils.buildTopRatedMovieUrl());
        }else {
            view.showToast("Network is not available");
        }
    }

    private class LoadMovieDbNetwork extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.loadStart();
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
            view.loadStop();
            PopularDataResponse response = new Gson().fromJson(s, PopularDataResponse.class);
            view.showResponseMovies(response);
//            Log.i("Project1", "onPostExecute: "+new Gson().toJson(response));
        }
    }
}
