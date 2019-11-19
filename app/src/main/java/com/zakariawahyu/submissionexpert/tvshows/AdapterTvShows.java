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

import com.zakariawahyu.submissionexpert.R;

import java.util.List;

public class AdapterTvShows extends RecyclerView.Adapter<AdapterTvShows.TvShowsViewHolder> {

    Context mContext;
    List<TvShowsItem> mData;

    public AdapterTvShows(Context mContext, List<TvShowsItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public TvShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_tv_shows,parent,false);
        return new TvShowsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowsViewHolder holder, final int position) {
        holder.judul.setText(mData.get(position).getJudul());
        holder.tanggal.setText(mData.get(position).getTanggal());
        holder.deskripsi.setText(mData.get(position).getDeskripsi());
        holder.poster.setImageResource(mData.get(position).getPoster());
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
            judul = itemView.findViewById(R.id.tvitemJudulTvShows);
            tanggal = itemView.findViewById(R.id.tvitemTanggalTvShows);
            deskripsi = itemView.findViewById(R.id.tvitemDeskripsiTvShows);
            poster = itemView.findViewById(R.id.tvitemPosterTvShows);
        }
    }
}
