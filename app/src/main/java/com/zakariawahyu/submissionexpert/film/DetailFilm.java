package com.zakariawahyu.submissionexpert.film;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.data.FilmHelper;

public class DetailFilm extends AppCompatActivity {

    boolean isFav = false;

    private TextView judul, tanggal, deskripsi;
    private ImageView poster;
    private Menu menu;
    private FilmHelper dataHelper;
    private ItemFilm itemFilm;
    private ProgressBar progressBar;



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

        String urlPoster = "https://image.tmdb.org/t/p/w185" + posterFilm;
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
            if (itemFilm != null) {
                dataHelper.open();
                if (isFav) {
                    isFav = false;
                    dataHelper.deleteFilmFav(itemFilm.getId());
                    setFavorite();
                } else {
                    isFav = true;
                    dataHelper.addFilmFav(itemFilm);
                    setFavorite();
                }
                dataHelper.close();
            }
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
}
