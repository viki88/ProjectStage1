package com.vikination.project1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vikination.project1.Models.PopularDataResponse;

/**
 * Created by Viki Andrianto on 7/8/17.
 */

public class DetailMovieActivity extends AppCompatActivity{

    public static final String MOVIE_DATA = "MOVIE_DATA";
    private PopularDataResponse.MovieData movieData;

    private TextView titleView, ratingView, synopsisView, releaseDateView;
    private ImageView posterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        movieData = (PopularDataResponse.MovieData) getIntent().getSerializableExtra(MOVIE_DATA);

        releaseDateView = (TextView) findViewById(R.id.release_date);
        titleView = (TextView) findViewById(R.id.title_movie);
        ratingView = (TextView) findViewById(R.id.rating);
        synopsisView = (TextView) findViewById(R.id.synopsis_movie);
        posterView = (ImageView) findViewById(R.id.poster_image);

        titleView.setText(movieData.getTitle());
        ratingView.setText(movieData.getVote_average()+"/10");
        synopsisView.setText(movieData.getOverview());
        releaseDateView.setText(movieData.getRelease_date());

        Picasso.with(this).load(NetworkUtils.MOVIE_DB_IMG_URL+movieData.getPoster_path()).into(posterView);

        getSupportActionBar().setTitle(R.string.detail_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }
}
