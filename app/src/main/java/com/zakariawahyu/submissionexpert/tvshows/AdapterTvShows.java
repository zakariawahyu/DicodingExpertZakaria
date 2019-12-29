package com.zakariawahyu.submissionexpert.tvshows;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.data.DataContract;

import java.util.ArrayList;

public class AdapterTvShows extends RecyclerView.Adapter<AdapterTvShows.TvShowsViewHolder> {

    Context mContext;
    private ArrayList<TvShowsItem> mData = new ArrayList<>();

    public AdapterTvShows(Context mContext, ArrayList<TvShowsItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setData(ArrayList<TvShowsItem> items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mData.clear();
        notifyDataSetChanged();
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
        holder.bind(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TvShowsItem tvShowsItem = mData.get(position);
                Intent intent = new Intent(mContext, TvShowsDetail.class);
                Uri uriId = Uri.parse(DataContract.TvShowsFavEntry.CONTENT_URI + "/"+ mData.get(position).getId());
                intent.setData(uriId);
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

        void bind(TvShowsItem tvShowsItem){
            final String urlPoster = "https://image.tmdb.org/t/p/w342" + tvShowsItem.getPoster();
            Picasso.get().load(urlPoster).into(poster);
            judul.setText(tvShowsItem.getJudul());
            tanggal.setText(tvShowsItem.getTanggal());
            deskripsi.setText(tvShowsItem.getDeskripsi());
        }
    }
}
