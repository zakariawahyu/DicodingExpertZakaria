package com.zakariawahyu.submissionexpert.film;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.zakariawahyu.submissionexpert.data.DataContract;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.zakariawahyu.submissionexpert.data.DataContract.getColumnInt;
import static com.zakariawahyu.submissionexpert.data.DataContract.getColumnString;

public class ItemFilm implements Parcelable {

    int id;
    String judul, tanggal, deskripsi, poster;

    public ItemFilm(JSONObject currentFilm) {
        try {
            int id = currentFilm.getInt("id");
            String judul = currentFilm.getString("title");
            String tanggal = currentFilm.getString("release_date");
            String description = currentFilm.getString("overview");
            String posterPath = currentFilm.getString("poster_path");

            this.id = id;
            this.judul = judul;
            this.tanggal = tanggal;
            this.deskripsi = description;
            this.poster = posterPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemFilm(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.judul = getColumnString(cursor, DataContract.FilmFavEntry.COL_JUDUL);
        this.tanggal = getColumnString(cursor, DataContract.FilmFavEntry.COL_TANGGAL);
        this.deskripsi = getColumnString(cursor, DataContract.FilmFavEntry.COL_DEKSRIPSI);
        this.poster = getColumnString(cursor, DataContract.FilmFavEntry.COL_POSTER);
    }

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
