package com.zakariawahyu.favkatalogfilm.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShowsItem implements Parcelable {

    int id;
    String judul, tanggal, deskripsi,poster;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    protected TvShowsItem(Parcel in) {
        id = in.readInt();
        judul = in.readString();
        tanggal = in.readString();
        deskripsi = in.readString();
        poster = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(judul);
        dest.writeString(tanggal);
        dest.writeString(deskripsi);
        dest.writeString(poster);
    }

    public TvShowsItem(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TvShowsItem> CREATOR = new Creator<TvShowsItem>() {
        @Override
        public TvShowsItem createFromParcel(Parcel in) {
            return new TvShowsItem(in);
        }

        @Override
        public TvShowsItem[] newArray(int size) {
            return new TvShowsItem[size];
        }
    };
}
