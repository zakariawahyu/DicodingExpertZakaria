package com.zakariawahyu.submissionexpert.film;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zakariawahyu.submissionexpert.R;

import java.util.List;

public class AdapterFilm extends RecyclerView.Adapter<AdapterFilm.FilmViewHolder> {

    Context mContext;
    List<ItemFilm> mData;

    public AdapterFilm(Context mContext, List<ItemFilm> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_film,parent,false);
        return new FilmViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, final int position) {
        holder.judul.setText(mData.get(position).getJudul());
        holder.tanggal.setText(mData.get(position).getTanggal());
        holder.deskripsi.setText(mData.get(position).getDeskripsi());
        holder.poster.setImageResource(mData.get(position).getPoster());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemFilm itemFilm = mData.get(position);
                Intent intent = new Intent(mContext, DetailFilm.class);
                intent.putExtra("Film",itemFilm );
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        TextView judul, tanggal, deskripsi;
        ImageView poster;

        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tvitemJudulFilm);
            tanggal = itemView.findViewById(R.id.tvitemTanggalFilm);
            deskripsi = itemView.findViewById(R.id.tvitemDeskripsiFilm);
            poster = itemView.findViewById(R.id.tvitemPosterFilm);
        }
    }
}
