package com.zakariawahyu.submissionexpert.tvshows;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.data.DataContract;
import com.zakariawahyu.submissionexpert.data.TvShowsHelper;
import com.zakariawahyu.submissionexpert.widget.WidgetFavorit;


import static android.provider.BaseColumns._ID;

public class TvShowsDetail extends AppCompatActivity {

    boolean isFav = false;

    private TextView judul, tanggal, deskripsi;
    private ImageView poster;
    private Menu menu;
    private TvShowsHelper dataHelper;
    private TvShowsItem tvShowsItem;
    private ProgressBar progressBar;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_shows_detail);

        judul = findViewById(R.id.tvJudulTvShows);
        tanggal = findViewById(R.id.tvTanggalTvShows);
        deskripsi = findViewById(R.id.tvDeskripsiTvShows);
        poster = findViewById(R.id.tvPosterTvShows);
        progressBar = findViewById(R.id.progressBarTvShows);

        dataHelper = TvShowsHelper.getInstance(getApplicationContext());

        uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            Log.d("id: ", "Id: " + getIntent().getData());
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    if (tvShowsItem != null) {
                        tvShowsItem = new TvShowsItem(cursor);
                    }
                }
                cursor.close();
            }
        }

        tvShowsItem = getIntent().getParcelableExtra("TvShows");
        String judulTvShows = tvShowsItem.getJudul();
        String tanggalTvShows = tvShowsItem.getTanggal();
        String deskripsiTvShows = tvShowsItem.getDeskripsi();
        String posterTvShows = tvShowsItem.getPoster();

        judul.setText(judulTvShows);
        tanggal.setText(tanggalTvShows);
        deskripsi.setText(deskripsiTvShows);

        String urlPoster = "https://image.tmdb.org/t/p/w342" + posterTvShows;
        Picasso.get().load(urlPoster).into(poster);
        showLoading(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        this.menu = menu;
        setFavorite();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_favorite) {
            if (tvShowsItem != null) {
                dataHelper.open();
                if (isFav) {
                    isFav = false;
                    deleteTvShows();
                    setFavorite();
                } else {
                    isFav = true;
                    saveTvShows();
                    setFavorite();
                }
                updateWidgetTvShowsFavorite();
                dataHelper.close();
            }
            updateWidgetTvShowsFavorite();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavorite() {
        MenuItem favorite = menu.findItem(R.id.menu_favorite);
        if (isFav) {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp));
        } else {
            favorite.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (tvShowsItem != null) {
            isFav = dataHelper.isTvShowsFav(tvShowsItem.getId());
            showLoading(false);
        }
        Log.d("IsAlreadyLove", String.valueOf(isFav));
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    private void saveTvShows(){
        dataHelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, tvShowsItem.getId());
        contentValues.put(DataContract.FilmFavEntry.COL_JUDUL, tvShowsItem.getJudul());
        contentValues.put(DataContract.FilmFavEntry.COL_TANGGAL, tvShowsItem.getTanggal());
        contentValues.put(DataContract.FilmFavEntry.COL_DEKSRIPSI, tvShowsItem.getDeskripsi());
        contentValues.put(DataContract.FilmFavEntry.COL_POSTER, tvShowsItem.getPoster());
        Uri insert = getContentResolver().insert(DataContract.TvShowsFavEntry.CONTENT_URI, contentValues);

        if (insert != null) {
            Toast.makeText(TvShowsDetail.this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TvShowsDetail.this, R.string.gagal_insert_fav, Toast.LENGTH_SHORT).show();
        }
        dataHelper.close();
    }

    private void deleteTvShows() {
        int delete = getContentResolver().delete(uri, null, null);

        if (delete == 0){
            Toast.makeText(TvShowsDetail.this, R.string.gagal_remove, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(TvShowsDetail.this, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateWidgetTvShowsFavorite() {
        Intent updateWidget = new Intent(this, WidgetFavorit.class);
        updateWidget.setAction(WidgetFavorit.UPDATE_WIDGET);
        sendBroadcast(updateWidget);
    }

}
