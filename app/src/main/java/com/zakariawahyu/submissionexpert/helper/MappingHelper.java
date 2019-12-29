package com.zakariawahyu.submissionexpert.helper;

import android.database.Cursor;

import com.zakariawahyu.submissionexpert.data.DataContract;
import com.zakariawahyu.submissionexpert.film.ItemFilm;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;


public class MappingHelper {

    public static ArrayList<ItemFilm> mapCursorFilmToArrayList(Cursor cursor) {
        if (cursor == null)
            return new ArrayList<>();

        ArrayList<ItemFilm> item = new ArrayList<>();
        while (cursor.moveToNext()) {
            ItemFilm itemFilm = new ItemFilm();
            itemFilm.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
            itemFilm.setJudul(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_JUDUL)));
            itemFilm.setTanggal(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_TANGGAL)));
            itemFilm.setDeskripsi(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_DEKSRIPSI)));
            itemFilm.setPoster(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_POSTER)));
            item.add(itemFilm);
        }
        return item;
    }

    public static ArrayList<TvShowsItem> mapCursorTvShowToArrayList(Cursor cursor) {
        if (cursor == null)
            return new ArrayList<>();
        ArrayList<TvShowsItem> item = new ArrayList<>();
        while (cursor.moveToNext()) {
            TvShowsItem tvShowsItem = new TvShowsItem();
            tvShowsItem.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
            tvShowsItem.setJudul(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_JUDUL)));
            tvShowsItem.setTanggal(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_TANGGAL)));
            tvShowsItem.setDeskripsi(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_DEKSRIPSI)));
            tvShowsItem.setPoster(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_POSTER)));
            item.add(tvShowsItem);
        }
        return item;
    }
}
