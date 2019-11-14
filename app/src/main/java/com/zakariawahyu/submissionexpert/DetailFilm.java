package com.zakariawahyu.submissionexpert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailFilm extends AppCompatActivity {

    TextView judul, tanggal, deskripsi;
    ImageView poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        judul = findViewById(R.id.tvJudul);
        tanggal = findViewById(R.id.tvTanggal);
        deskripsi = findViewById(R.id.tvDeskripsi);
        poster = findViewById(R.id.tvPoster);

        ItemFilm item = getIntent().getParcelableExtra("Film");
        String judulFilm = item.getJudul();
        String tanggalFilm = item.getTanggal();
        String deskripsiFilm = item.getDeskripsi();
        int posterFilm = item.getPoster();

        judul.setText(judulFilm);
        tanggal.setText(tanggalFilm);
        deskripsi.setText(deskripsiFilm);
        poster.setImageResource(posterFilm);
    }
}
