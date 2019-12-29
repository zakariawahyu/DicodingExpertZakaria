package com.zakariawahyu.favkatalogfilm.adapter;

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
import com.zakariawahyu.favkatalogfilm.DetailActivity;
import com.zakariawahyu.favkatalogfilm.R;
import com.zakariawahyu.favkatalogfilm.database.DataContract;
import com.zakariawahyu.favkatalogfilm.model.ItemFilm;

import java.util.ArrayList;

public class AdapterFilmFav extends RecyclerView.Adapter<AdapterFilmFav.FilmViewHolder> {

    Context mContext;
    private ArrayList<ItemFilm> mData = new ArrayList<>();

    public AdapterFilmFav(Context mContext, ArrayList<ItemFilm> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    public ArrayList<ItemFilm> getListData() {
        return mData;
    }


    public void setListFilm(ArrayList<ItemFilm> listData) {
        if (listData.size() > 0){
            this.mData.clear();
        }
        this.mData.addAll(listData);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new FilmViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, final int position) {
        holder.bind(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemFilm itemFilm = mData.get(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
                Uri uriId = Uri.parse(DataContract.FilmFavEntry.CONTENT_URI + "/"+ mData.get(position).getId());
                intent.setData(uriId);
                intent.putExtra(DetailActivity.EXTRA_FILM, itemFilm );
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
            judul = itemView.findViewById(R.id.tvitemJudul);
            tanggal = itemView.findViewById(R.id.tvitemTanggal);
            deskripsi = itemView.findViewById(R.id.tvitemDeskripsi);
            poster = itemView.findViewById(R.id.tvitemPoster);
        }

        void bind(ItemFilm itemFilm) {
            final String urlPoster = "https://image.tmdb.org/t/p/w342" + itemFilm.getPoster();
            Picasso.get().load(urlPoster).into(poster);
            judul.setText(itemFilm.getJudul());
            tanggal.setText(itemFilm.getTanggal());
            deskripsi.setText(itemFilm.getDeskripsi());
        }
    }
}
