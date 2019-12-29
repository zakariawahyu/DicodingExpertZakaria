package com.zakariawahyu.submissionexpert.tvshows;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.zakariawahyu.submissionexpert.data.DataContract;

import org.json.JSONObject;

import static android.provider.BaseColumns._ID;
import static com.zakariawahyu.submissionexpert.data.DataContract.getColumnInt;
import static com.zakariawahyu.submissionexpert.data.DataContract.getColumnString;

public class TvShowsItem implements Parcelable {

    int id;
    String judul, tanggal, deskripsi,poster;

    public TvShowsItem(JSONObject currentTvShows) {
        try {
            int id = currentTvShows.getInt("id");
            String judul = currentTvShows.getString("name");
            String tanggal = currentTvShows.getString("first_air_date");
            String description = currentTvShows.getString("overview");
            String posterPath = currentTvShows.getString("poster_path");

            this.id = id;
            this.judul = judul;
            this.tanggal = tanggal;
            this.deskripsi = description;
            this.poster = posterPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public TvShowsItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.judul = getColumnString(cursor, DataContract.TvShowsFavEntry.COL_JUDUL);
        this.tanggal = getColumnString(cursor, DataContract.TvShowsFavEntry.COL_TANGGAL);
        this.deskripsi = getColumnString(cursor, DataContract.TvShowsFavEntry.COL_DEKSRIPSI);
        this.poster = getColumnString(cursor, DataContract.TvShowsFavEntry.COL_POSTER);
    }

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
