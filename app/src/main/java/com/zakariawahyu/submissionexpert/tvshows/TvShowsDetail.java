package com.zakariawahyu.submissionexpert.tvshows;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.film.ItemFilm;

public class TvShowsDetail extends AppCompatActivity {

    private TextView judul, tanggal, deskripsi;
    private ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_shows_detail);

        judul = findViewById(R.id.tvJudulTvShows);
        tanggal = findViewById(R.id.tvTanggalTvShows);
        deskripsi = findViewById(R.id.tvDeskripsiTvShows);
        poster = findViewById(R.id.tvPosterTvShows);

        TvShowsItem item = getIntent().getParcelableExtra("TvShows");
        String judulTvShows = item.getJudul();
        String tanggalTvShows = item.getTanggal();
        String deskripsiTvShows = item.getDeskripsi();
        int posterTvShows = item.getPoster();

        judul.setText(judulTvShows);
        tanggal.setText(tanggalTvShows);
        deskripsi.setText(deskripsiTvShows);
        poster.setImageResource(posterTvShows);
    }
}
