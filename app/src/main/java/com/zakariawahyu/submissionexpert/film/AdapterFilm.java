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

import com.squareup.picasso.Picasso;
import com.zakariawahyu.submissionexpert.R;

import java.util.ArrayList;

public class AdapterFilm extends RecyclerView.Adapter<AdapterFilm.FilmViewHolder> {

    Context mContext;
    private ArrayList<ItemFilm> mData = new ArrayList<>();

    public AdapterFilm(Context mContext, ArrayList<ItemFilm> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setData(ArrayList<ItemFilm> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
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
        holder.bind(mData.get(position));
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

        void bind(ItemFilm itemFilm) {
            final String urlPoster = "https://image.tmdb.org/t/p/w185" + itemFilm.getPoster();
            Picasso.get().load(urlPoster).into(poster);
            judul.setText(itemFilm.getJudul());
            tanggal.setText(itemFilm.getTanggal());
            deskripsi.setText(itemFilm.getDeskripsi());
        }
    }
}
