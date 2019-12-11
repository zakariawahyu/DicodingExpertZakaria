package com.zakariawahyu.submissionexpert.film;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zakariawahyu.submissionexpert.R;

public class DetailFilm extends AppCompatActivity {

    private TextView judul, tanggal, deskripsi;
    private ImageView poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        judul = findViewById(R.id.tvJudulFilm);
        tanggal = findViewById(R.id.tvTanggalFilm);
        deskripsi = findViewById(R.id.tvDeskripsiFilm);
        poster = findViewById(R.id.tvPosterFilm);

        ItemFilm item = getIntent().getParcelableExtra("Film");
        String judulFilm = item.getJudul();
        String tanggalFilm = item.getTanggal();
        String deskripsiFilm = item.getDeskripsi();
        String posterFilm = item.getPoster();

        judul.setText(judulFilm);
        tanggal.setText(tanggalFilm);
        deskripsi.setText(deskripsiFilm);

        String urlPoster = "https://image.tmdb.org/t/p/w185" + posterFilm;
        Picasso.get().load(urlPoster).into(poster);
    }
}
