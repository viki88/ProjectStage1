package com.vikination.project1;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vikination.project1.Models.PopularDataResponse;
import com.vikination.project1.Models.ReviewsDataResponse;
import com.vikination.project1.Models.TrailerDataResponse;
import com.vikination.project1.data.FavContract;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Viki Andrianto on 7/8/17.
 */

public class DetailMovieActivity extends AppCompatActivity implements DetailMovieView{

    public static final String MOVIE_DATA = "MOVIE_DATA";
    public static final String FAV_DETAIL_EXTRA = "fav-detail_extra";
    public static final String SCROLL_POSITION = "scroll-position";
    private PopularDataResponse.MovieData movieData;

    private TextView titleView, ratingView, synopsisView, releaseDateView, favText;
    private ScrollView scrollViewDetail;
    private ImageView posterView;
    private ProgressDialog progressDialog;
    private DetailMoviePresenter presenter;
    private LinearLayout layoutLinearTrailer, liniearLayoutReview;
    private ArrayList<TrailerDataResponse.TrailerData> dataTrailers;
    private ArrayList<ReviewsDataResponse.ReviewData> dataReviews;

    private boolean isFavDetail = false;
    byte[] imagePosterBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.text_loading_text));

        presenter = new DetailMoviePresenter(this);

        movieData = (PopularDataResponse.MovieData) getIntent().getSerializableExtra(MOVIE_DATA);
        isFavDetail = getIntent().getBooleanExtra(FAV_DETAIL_EXTRA, false);

        releaseDateView = (TextView) findViewById(R.id.release_date);
        titleView = (TextView) findViewById(R.id.title_movie);
        ratingView = (TextView) findViewById(R.id.rating);
        synopsisView = (TextView) findViewById(R.id.synopsis_movie);
        posterView = (ImageView) findViewById(R.id.poster_image);
        layoutLinearTrailer = (LinearLayout) findViewById(R.id.ll_trailer_list);
        liniearLayoutReview = (LinearLayout) findViewById(R.id.ll_reviews_list);
        favText = (TextView) findViewById(R.id.tv_mark);
        scrollViewDetail = (ScrollView) findViewById(R.id.scrollViewDetail);

        if (isFavDetail) favText.setText(R.string.text_button_unmark);
        else favText.setText(R.string.text_button_mark);

        favText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavDetail)addDataToDatabase(movieData);
                else showDialogBeforeRemove();
            }
        });

        titleView.setText(movieData.getTitle());
        ratingView.setText(movieData.getVote_average()+"/10");
        synopsisView.setText(movieData.getOverview());
        releaseDateView.setText(movieData.getRelease_date());

        getSupportActionBar().setTitle(R.string.detail_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (NetworkUtils.isOnline(this)){
            Picasso.with(this).load(NetworkUtils.MOVIE_DB_IMG_URL+movieData.getPoster_path()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imagePosterBitmap = stream.toByteArray();
                    posterView.setImageBitmap(BitmapFactory.decodeByteArray(imagePosterBitmap, 0, imagePosterBitmap.length));
//                bitmap.recycle();
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
            presenter.loadMovieVideo(String.valueOf(movieData.getId()));
            presenter.loadReviewsVideo(String.valueOf(movieData.getId()));
        }else {
            if (movieData.getOffline_image() != null){
                imagePosterBitmap = movieData.getOffline_image();
                posterView.setImageBitmap(BitmapFactory.decodeByteArray(imagePosterBitmap, 0, imagePosterBitmap.length));
            }else {
                showToast(getString(R.string.text_connection_failed));
            }
            if (movieData.getOffline_trailer() != null) {
                ArrayList<TrailerDataResponse.TrailerData> dataTrailers =
                        new Gson().fromJson(movieData.getOffline_trailer(),
                                new TypeToken<List<TrailerDataResponse.TrailerData>>(){}.getType());
                ArrayList<ReviewsDataResponse.ReviewData> dataReviews =
                        new Gson().fromJson(movieData.getOffline_review(),
                                new TypeToken<List<ReviewsDataResponse.ReviewData>>(){}.getType());
                trailerResult(dataTrailers);
                reviewsResult(dataReviews);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(Utils.TAG, "onSaveInstanceState: "+scrollViewDetail.getScrollY());
        outState.putIntArray(SCROLL_POSITION, new int[]{scrollViewDetail.getScrollX(),scrollViewDetail.getScrollY()});
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] postion = savedInstanceState.getIntArray(SCROLL_POSITION);
        Log.i(Utils.TAG, "onRestoreInstanceState: "+postion[1]);
        if (postion != null){
            scrollViewDetail.scrollTo(postion[0],postion[1]);
        }
    }

    void showDialogBeforeRemove(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_fav);
        builder.setMessage(R.string.text_unmark_fav);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFavById(movieData.getId());
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    void addDataToDatabase(PopularDataResponse.MovieData movieData){
        ContentValues cv = new ContentValues();
        cv.put(FavContract.FavEntry.COLLUMN_NAME_ID,movieData.getId());
        cv.put(FavContract.FavEntry.COLLUMN_NAME_TITLE,movieData.getTitle());
        cv.put(FavContract.FavEntry.COLLUMN_MOVIE_POSTER_IMAGE,movieData.getPoster_path());
        cv.put(FavContract.FavEntry.COLLUMN_IMAGE_BYTE,imagePosterBitmap);
        cv.put(FavContract.FavEntry.COLLUMN_RELEASE_DATE,movieData.getRelease_date());
        cv.put(FavContract.FavEntry.COLLUMN_RATING,movieData.getVote_average());
        cv.put(FavContract.FavEntry.COLLUMN_REVIEW,movieData.getOverview());
        Log.i(Utils.TAG, "addDataToDatabase: data reviews -> "+new Gson().toJson(dataReviews));
        cv.put(FavContract.FavEntry.COLLUMN_REVIEWS,new Gson().toJson(dataReviews));
        cv.put(FavContract.FavEntry.COLLUMN_TRAILERS,new Gson().toJson(dataTrailers));

        Uri uri = getContentResolver().insert(FavContract.FavEntry.CONTENT_URI, cv);
        if (uri != null){
            showToast(getString(R.string.toast_add_fav_success));
        }else showToast(getString(R.string.toast_add_fav_failed));
    }

    void deleteFavById(int id){
        int status = getContextView().getContentResolver().delete(FavContract.FavEntry.CONTENT_URI
                , FavContract.FavEntry.COLLUMN_NAME_ID +" = ?"
                , new String[]{String.valueOf(id)});

        if (status > 0) {
            showToast(getString(R.string.toast_delete_fav_success));
            finish();
        }else showToast(getString(R.string.toast_delete_fav_failed));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_share:
                Utils.shareVideo(DetailMovieActivity.this, getContentShare());
                break;
            default:
                break;
        }
        return true;
    }

    String getContentShare(){
        return "Watch video trailer of \""+movieData.getTitle()+"\" on this link : http://youtube.com/"
                +dataTrailers.get(0).getKey();
    }

    @Override
    public void loadStart() {
        progressDialog.show();
    }

    @Override
    public void loadStop() {
        progressDialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContextView() {
        return this;
    }

    @Override
    public void trailerResult(ArrayList<TrailerDataResponse.TrailerData> datas) {
        dataTrailers = datas;
        layoutLinearTrailer.removeAllViews();
        for (int i = 0; i < dataTrailers.size(); i++) {
            layoutLinearTrailer.addView(getListTrailerView(datas.get(i)));
        }
    }

    @Override
    public void reviewsResult(ArrayList<ReviewsDataResponse.ReviewData> datas) {
        dataReviews = datas;
        liniearLayoutReview.removeAllViews();
        for (int i = 0; i < dataReviews.size(); i++) {
            liniearLayoutReview.addView(getRowReviewView(datas.get(i)));
        }
    }

    View getListTrailerView(final TrailerDataResponse.TrailerData data){
        View view = LayoutInflater.from(this).inflate(R.layout.row_list_trailer, null);
        ((TextView)view.findViewById(R.id.tv_trailer_name)).setText(data.getName());
        view.findViewById(R.id.iv_play_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.watchYoutubeVideo(data.getKey(), DetailMovieActivity.this);
            }
        });
        return view;
    }

    View getRowReviewView(ReviewsDataResponse.ReviewData data){
        View view = LayoutInflater.from(this).inflate(R.layout.row_list_review, null);
        ((TextView)view.findViewById(R.id.tv_author_review)).setText(data.getAuthor());
        ((TextView)view.findViewById(R.id.tv_content_review)).setText(data.getContent());
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
