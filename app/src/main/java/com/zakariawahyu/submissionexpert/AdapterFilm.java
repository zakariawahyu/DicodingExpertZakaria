package com.zakariawahyu.submissionexpert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterFilm extends ArrayAdapter<ItemFilm> {
    public AdapterFilm(@NonNull Context context,  @NonNull List<ItemFilm> itemfilm) {
        super(context, 0, itemfilm);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ItemFilm film = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_film, parent, false);
        }

        TextView tvJudul = convertView.findViewById(R.id.tvitemJudul);
        TextView tvTanggal = convertView.findViewById(R.id.tvitemTanggal);
        TextView tvDeskipsi = convertView.findViewById(R.id.tvitemDeskripsi);
        ImageView tvPoster = convertView.findViewById(R.id.tvitemPoster);

        tvJudul.setText(film.judul);
        tvTanggal.setText(film.tanggal);
        tvDeskipsi.setText(film.deskripsi + "...");
        tvPoster.setImageResource(film.poster);

        return convertView;
    }
}
