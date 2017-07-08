package com.vikination.project1;

import android.content.Context;

import com.vikination.project1.Models.PopularDataResponse;

/**
 * Created by Viki Andrianto on 7/8/17.
 */

interface MainActivityView {

    void loadStart();
    void loadStop();
    void showToast(String msg);
    void showResponseMovies(PopularDataResponse dataResponse);
    Context getContextView();
}
