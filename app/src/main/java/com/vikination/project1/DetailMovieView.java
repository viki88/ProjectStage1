package com.vikination.project1;

import android.content.Context;

import com.vikination.project1.Models.PopularDataResponse;
import com.vikination.project1.Models.ReviewsDataResponse;
import com.vikination.project1.Models.TrailerDataResponse;

import java.util.ArrayList;

/**
 * Created by Viki Andrianto on 8/8/17.
 */

interface DetailMovieView {
    void loadStart();
    void loadStop();
    void showToast(String msg);
    void trailerResult(ArrayList<TrailerDataResponse.TrailerData> datas);
    void reviewsResult(ArrayList<ReviewsDataResponse.ReviewData> datas);
    Context getContextView();
}
