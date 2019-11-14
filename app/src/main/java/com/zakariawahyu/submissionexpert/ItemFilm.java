package com.zakariawahyu.submissionexpert;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemFilm implements Parcelable {

    String judul, tanggal, deskripsi;
    int poster;

    public ItemFilm() {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.judul);
        dest.writeString(this.tanggal);
        dest.writeString(this.deskripsi);
        dest.writeInt(this.poster);
    }

    protected ItemFilm(Parcel in) {
        this.judul = in.readString();
        this.tanggal = in.readString();
        this.deskripsi = in.readString();
        this.poster = in.readInt();
    }

    public static final Parcelable.Creator<ItemFilm> CREATOR = new Parcelable.Creator<ItemFilm>() {
        @Override
        public ItemFilm createFromParcel(Parcel source) {
            return new ItemFilm(source);
        }

        @Override
        public ItemFilm[] newArray(int size) {
            return new ItemFilm[size];
        }
    };
}
