package com.zakariawahyu.submissionexpert.tvshows;

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
import com.zakariawahyu.submissionexpert.data.TvShowsHelper;

public class TvShowsDetail extends AppCompatActivity {

    boolean isFav = false;

    private TextView judul, tanggal, deskripsi;
    private ImageView poster;
    private Menu menu;
    private TvShowsHelper dataHelper;
    private TvShowsItem tvShowsItem;
    private ProgressBar progressBar;

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

        tvShowsItem = getIntent().getParcelableExtra("TvShows");
        String judulTvShows = tvShowsItem.getJudul();
        String tanggalTvShows = tvShowsItem.getTanggal();
        String deskripsiTvShows = tvShowsItem.getDeskripsi();
        String posterTvShows = tvShowsItem.getPoster();

        judul.setText(judulTvShows);
        tanggal.setText(tanggalTvShows);
        deskripsi.setText(deskripsiTvShows);

        String urlPoster = "https://image.tmdb.org/t/p/w185" + posterTvShows;
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
                    dataHelper.deleteTvShowsFav(tvShowsItem.getId());
                    setFavorite();
                } else {
                    isFav = true;
                    dataHelper.addTvShowsFav(tvShowsItem);
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
}
