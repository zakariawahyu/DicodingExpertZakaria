package com.zakariawahyu.submissionexpert.tvshows;

import android.os.Parcel;
import android.os.Parcelable;

public class TvShowsItem implements Parcelable {

    String judul, tanggal, deskripsi;
    int poster;

    public TvShowsItem() {
        this.judul = judul;
        this.tanggal = tanggal;
        this.deskripsi = deskripsi;
        this.poster = poster;
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

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }


    protected TvShowsItem(Parcel in) {
        judul = in.readString();
        tanggal = in.readString();
        deskripsi = in.readString();
        poster = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(judul);
        dest.writeString(tanggal);
        dest.writeString(deskripsi);
        dest.writeInt(poster);
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
