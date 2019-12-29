package com.zakariawahyu.favkatalogfilm.model;

import android.os.Parcel;
import android.os.Parcelable;





public class ItemFilm implements Parcelable {

    int id;
    String judul, tanggal, deskripsi, poster;

    public int getId(){
        return id;
    }

    public void setId(int id){
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.tanggal);
        dest.writeString(this.deskripsi);
        dest.writeString(this.poster);
    }

    protected ItemFilm(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.tanggal = in.readString();
        this.deskripsi = in.readString();
        this.poster = in.readString();
    }

    public ItemFilm(){

    }

    public static final Creator<ItemFilm> CREATOR = new Creator<ItemFilm>() {
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
