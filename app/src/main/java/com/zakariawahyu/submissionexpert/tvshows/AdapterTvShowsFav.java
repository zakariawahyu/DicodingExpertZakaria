package com.zakariawahyu.submissionexpert.tvshows;

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
import com.zakariawahyu.submissionexpert.film.AdapterFilmFav;
import com.zakariawahyu.submissionexpert.film.DetailFilm;
import com.zakariawahyu.submissionexpert.film.ItemFilm;

import java.util.ArrayList;

public class AdapterTvShowsFav extends RecyclerView.Adapter<AdapterTvShowsFav.TvShowsViewHolder> {

    Context mContext;
    private ArrayList<TvShowsItem> mData = new ArrayList<>();

    public AdapterTvShowsFav(Context mContext, ArrayList<TvShowsItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    public ArrayList<TvShowsItem> getListData() {
        return mData;
    }


    public void setListTvShows(ArrayList<TvShowsItem> listData) {
        if (listData.size() > 0){
            this.mData.clear();
        }
        this.mData.addAll(listData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TvShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_film,parent,false);
        return new TvShowsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowsViewHolder holder, final int position) {
        holder.bind(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TvShowsItem tvShowsItem = mData.get(position);
                Intent intent = new Intent(mContext, TvShowsDetail.class);
                intent.putExtra("TvShows",tvShowsItem );
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TvShowsViewHolder extends RecyclerView.ViewHolder {
        TextView judul, tanggal, deskripsi;
        ImageView poster;

        public TvShowsViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tvitemJudulFilm);
            tanggal = itemView.findViewById(R.id.tvitemTanggalFilm);
            deskripsi = itemView.findViewById(R.id.tvitemDeskripsiFilm);
            poster = itemView.findViewById(R.id.tvitemPosterFilm);
        }

        void bind(TvShowsItem tvShowsItem) {
            final String urlPoster = "https://image.tmdb.org/t/p/w185" + tvShowsItem.getPoster();
            Picasso.get().load(urlPoster).into(poster);
            judul.setText(tvShowsItem.getJudul());
            tanggal.setText(tvShowsItem.getTanggal());
            deskripsi.setText(tvShowsItem.getDeskripsi());
        }
    }
}
