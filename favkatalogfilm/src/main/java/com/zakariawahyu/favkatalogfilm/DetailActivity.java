package com.zakariawahyu.favkatalogfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zakariawahyu.favkatalogfilm.model.ItemFilm;
import com.zakariawahyu.favkatalogfilm.model.TvShowsItem;

public class DetailActivity extends AppCompatActivity {

    private TextView judul, tanggal, deskripsi;
    private ImageView poster;

    public static final String EXTRA_FILM= "Film";
    public static final String EXTRA_TV_SHOW = "TvShows";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        judul = findViewById(R.id.tvJudul);
        tanggal = findViewById(R.id.tvTanggal);
        deskripsi = findViewById(R.id.tvDeskripsi);
        poster = findViewById(R.id.tvPoster);

        setData();
    }

    private void setData(){
        ItemFilm itemFilm = getIntent().getParcelableExtra(EXTRA_FILM);
        TvShowsItem tvShowsItem = getIntent().getParcelableExtra(EXTRA_TV_SHOW);

        if (itemFilm != null) {
            String judulFilm = itemFilm.getJudul();
            String tanggalFilm = itemFilm.getTanggal();
            String deskripsiFilm = itemFilm.getDeskripsi();
            String posterFilm = itemFilm.getPoster();

            judul.setText(judulFilm);
            tanggal.setText(tanggalFilm);
            deskripsi.setText(deskripsiFilm);

            String urlPoster = "https://image.tmdb.org/t/p/w342" + posterFilm;
            Picasso.get().load(urlPoster).into(poster);

        } else if (tvShowsItem != null) {
            String judulTvShows = tvShowsItem.getJudul();
            String tanggalTvShows = tvShowsItem.getTanggal();
            String deskripsiTvShows = tvShowsItem.getDeskripsi();
            String posterTvShows = tvShowsItem.getPoster();

            judul.setText(judulTvShows);
            tanggal.setText(tanggalTvShows);
            deskripsi.setText(deskripsiTvShows);

            String urlPoster = "https://image.tmdb.org/t/p/w342" + posterTvShows;
            Picasso.get().load(urlPoster).into(poster);
        }
    }

}
