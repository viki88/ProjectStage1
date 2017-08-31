package com.vikination.project1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vikination.project1.Models.PopularDataResponse;
import com.vikination.project1.data.FavContract;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityView, OnThumbClickListener
        ,LoaderManager.LoaderCallbacks<Cursor>{

    private MainPresenterImpl presenter;
    private RecyclerView recyclerView;
    private ThumbnailImageAdapter adapter;
    private SwipeRefreshLayout swipeLayout;

    private static final int NUMBER_COLLUMN = 2;
    private static final String POP_MOVIE_TITLE = "Pop Movies";
    private static final String TOP_MOVIE_TITLE = "Top Movies";
    private static final String FAV_MOVIE_TITLE = "Favourite Movies";
    private String tag = POP_MOVIE_TITLE;

    public static final int FAV_LOADER_ID = 20;
    private boolean favData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ThumbnailImageAdapter(this, this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMovies();
            }
        });
        recyclerView.setAdapter(adapter);
        GridLayoutManager sGridLayout = new GridLayoutManager(this, NUMBER_COLLUMN);
//        sGridLayout.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(sGridLayout);

        presenter = new MainPresenterImpl(this);
        setTitle(POP_MOVIE_TITLE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_popular:
                setTitle(POP_MOVIE_TITLE);
                loadMovies();
                break;
            case R.id.menu_top_rated:
                setTitle(TOP_MOVIE_TITLE);
                loadMovies();
                break;
            case R.id.menu_fav:
                setTitle(FAV_MOVIE_TITLE);
                loadMovies();
                break;
            default:
                break;
        }
        return true;
    }

    private void setTitle(String title){
        tag = title;
        getSupportActionBar().setTitle(title);
    }

    private void loadMovies(){
        if (tag.equals(POP_MOVIE_TITLE)){
            favData = false;
            presenter.loadMoviewPoster();
        }else if (tag.equals(TOP_MOVIE_TITLE)){
            favData = false;
            presenter.loadTopMoviePoster();
        }else {
            favData = true;
            getSupportLoaderManager().restartLoader(FAV_LOADER_ID, null, this);
//            presenter.loadFavMoviePoster();
        }
    }

    private void showDetailMovie(PopularDataResponse.MovieData data){
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(DetailMovieActivity.MOVIE_DATA, data);
        intent.putExtra(DetailMovieActivity.FAV_DETAIL_EXTRA, favData);
        startActivity(intent);
    }

    @Override
    public void loadStart() {}

    @Override
    public void loadStop() {
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResponseMovies(PopularDataResponse dataResponse) {
        adapter.updateData(dataResponse.getResults());
    }

    @Override
    public Context getContextView() {
        return this;
    }

    @Override
    public void onClickThumb(PopularDataResponse.MovieData movieData) {
//        showToast(movieData.getTitle());
        showDetailMovie(movieData);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor favDataCursor;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (favDataCursor != null){
                    deliverResult(favDataCursor);
                }else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                return getContextView().getContentResolver().query(FavContract.FavEntry.CONTENT_URI
                ,null,null,null,FavContract.FavEntry._ID);
            }

            @Override
            public void deliverResult(Cursor data) {
                favDataCursor = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<PopularDataResponse.MovieData> dataMovies = new ArrayList<>();
        while (data.moveToNext()){
//            String dataContent = data.getString(data.getColumnIndex(FavContract.FavEntry.COLLUMN_NAME_TITLE));
            PopularDataResponse.MovieData movie = new PopularDataResponse.MovieData();
            movie.setId(data.getInt(data.getColumnIndex(FavContract.FavEntry.COLLUMN_NAME_ID)));
            movie.setTitle(data.getString(data.getColumnIndex(FavContract.FavEntry.COLLUMN_NAME_TITLE)));
            movie.setOffline_image(data.getBlob(data.getColumnIndex(FavContract.FavEntry.COLLUMN_IMAGE_BYTE)));
            movie.setPoster_path(data.getString(data.getColumnIndex(FavContract.FavEntry.COLLUMN_MOVIE_POSTER_IMAGE)));
            movie.setRelease_date(data.getString(data.getColumnIndex(FavContract.FavEntry.COLLUMN_RELEASE_DATE)));
            movie.setVote_average(Double.parseDouble(
                    data.getString(data.getColumnIndex(FavContract.FavEntry.COLLUMN_RATING))));
            movie.setOverview(data.getString(data.getColumnIndex(FavContract.FavEntry.COLLUMN_REVIEW)));
            movie.setOffline_review(data.getString(data.getColumnIndex(FavContract.FavEntry.COLLUMN_REVIEWS)));
            movie.setOffline_trailer(data.getString(data.getColumnIndex(FavContract.FavEntry.COLLUMN_TRAILERS)));
//            movie.set(data.getString(data.getColumnIndex(FavContract.FavEntry.COLLUMN_MOVIE_POSTER_IMAGE)));
            dataMovies.add(movie);
        }
        adapter.updateData(dataMovies);
        loadStop();
//        Log.i(Utils.TAG, "onLoadFinished: "+new Gson().toJson(dataMovies));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public static class ThumbnailImageAdapter extends RecyclerView.Adapter<ThumbnailImageAdapter.ViewHolderImage>{

        ArrayList<PopularDataResponse.MovieData> data = new ArrayList();
        final Context context;
        final OnThumbClickListener listener;

        public ThumbnailImageAdapter(Context context, OnThumbClickListener listener){
            this.context = context;
            this.listener = listener;
        }

        @Override
        public ViewHolderImage onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_image_thumbnail, parent, false);
            return new ViewHolderImage(view,listener);
        }

        @Override
        public void onBindViewHolder(final ViewHolderImage holder, int position) {
            final PopularDataResponse.MovieData dataMovie = data.get(position);
            if (NetworkUtils.isOnline(context))Picasso.with(context)
                    .load(NetworkUtils.MOVIE_DB_IMG_URL+dataMovie.getPoster_path()).into(holder.imageView);
            else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(
                        dataMovie.getOffline_image(), 0, dataMovie.getOffline_image().length);
                holder.imageView.setImageBitmap(bitmap);
            }
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.onClickThumbs(dataMovie);
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void updateData(ArrayList<PopularDataResponse.MovieData> data){
            this.data = data;
            notifyDataSetChanged();
        }

        class ViewHolderImage extends RecyclerView.ViewHolder{

            public final ImageView imageView;
            final OnThumbClickListener listener;

            public ViewHolderImage(View itemView, OnThumbClickListener listener) {
                super(itemView);

                imageView = (ImageView) itemView.findViewById(R.id.image_view_movie);
                this.listener = listener;
            }

            void onClickThumbs(PopularDataResponse.MovieData movieData){
                listener.onClickThumb(movieData);
            }
        }
    }


}
