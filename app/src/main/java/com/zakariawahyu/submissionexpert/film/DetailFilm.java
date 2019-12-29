package com.zakariawahyu.submissionexpert.film;

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
import com.zakariawahyu.submissionexpert.data.FilmHelper;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;
import com.zakariawahyu.submissionexpert.widget.WidgetFavorit;

import static android.provider.BaseColumns._ID;

public class DetailFilm extends AppCompatActivity {

    boolean isFav = false;

    private TextView judul, tanggal, deskripsi;
    private ImageView poster;
    private Menu menu;
    private FilmHelper dataHelper;
    private ItemFilm itemFilm;
    private ProgressBar progressBar;
    private Uri uriId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        judul = findViewById(R.id.tvJudulFilm);
        tanggal = findViewById(R.id.tvTanggalFilm);
        deskripsi = findViewById(R.id.tvDeskripsiFilm);
        poster = findViewById(R.id.tvPosterFilm);
        progressBar = findViewById(R.id.progressBarFilm);

        dataHelper = FilmHelper.getInstance(getApplicationContext());

        itemFilm = getIntent().getParcelableExtra("Film");
        String judulFilm = itemFilm.getJudul();
        String tanggalFilm = itemFilm.getTanggal();
        String deskripsiFilm = itemFilm.getDeskripsi();
        String posterFilm = itemFilm.getPoster();

        judul.setText(judulFilm);
        tanggal.setText(tanggalFilm);
        deskripsi.setText(deskripsiFilm);

        String urlPoster = "https://image.tmdb.org/t/p/w342" + posterFilm;
        Picasso.get().load(urlPoster).into(poster);
        showLoading(true);

        uriId = getIntent().getData();
        if (uriId != null) {
            Cursor cursor = getContentResolver().query(uriId, null, null, null, null);
            Log.d("id: ", "Id: " + getIntent().getData());
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    if (itemFilm != null) {
                        itemFilm = new ItemFilm(cursor);
                    }
                }
                cursor.close();
            }
        }
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
            if (itemFilm != null) {
                dataHelper.open();
                if (isFav) {
                    isFav = false;
                    deleteFilm();
                    setFavorite();
                } else {
                    isFav = true;
                    saveFavFilm();
                    setFavorite();
                }
                updateWidgetFilmFavorite();
                dataHelper.close();
            }
            updateWidgetFilmFavorite();
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
        if (itemFilm != null) {
            isFav = dataHelper.isFilmFav(itemFilm.getId());
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

    private void saveFavFilm(){
        dataHelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, itemFilm.getId());
        contentValues.put(DataContract.FilmFavEntry.COL_JUDUL, itemFilm.getJudul());
        contentValues.put(DataContract.FilmFavEntry.COL_TANGGAL, itemFilm.getTanggal());
        contentValues.put(DataContract.FilmFavEntry.COL_DEKSRIPSI, itemFilm.getDeskripsi());
        contentValues.put(DataContract.FilmFavEntry.COL_POSTER, itemFilm.getPoster());
        Uri insert = getContentResolver().insert(DataContract.FilmFavEntry.CONTENT_URI, contentValues);

        if (insert != null) {
            Toast.makeText(DetailFilm.this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailFilm.this, R.string.gagal_insert_fav, Toast.LENGTH_SHORT).show();
        }
        dataHelper.close();
    }

    private void deleteFilm() {
        int delete = getContentResolver().delete(uriId, null, null);

        if (delete == 0){
            Toast.makeText(DetailFilm.this, R.string.gagal_remove, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailFilm.this, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateWidgetFilmFavorite() {
        Intent updateWidget = new Intent(this, WidgetFavorit.class);
        updateWidget.setAction(WidgetFavorit.UPDATE_WIDGET);
        sendBroadcast(updateWidget);
    }
}
