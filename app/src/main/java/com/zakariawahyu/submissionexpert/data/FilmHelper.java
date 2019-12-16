package com.zakariawahyu.submissionexpert.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zakariawahyu.submissionexpert.film.ItemFilm;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class FilmHelper {

    private static DataHelper dbHelper;
    private static FilmHelper INSTANCE;

    private static SQLiteDatabase db;

    private FilmHelper(Context context) {
        dbHelper = new DataHelper(context);
    }

    public static FilmHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FilmHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        if (db.isOpen())
            db.close();
    }

    public ArrayList<ItemFilm> getAllFilmFav() {
        ArrayList<ItemFilm> filmArrayList = new ArrayList<>();
        Cursor cursor = db.query(DataContract.FilmFavEntry.TABLE_FILM,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();


        if (cursor.getCount() > 0) {
            do {
                ItemFilm itemFilm = new ItemFilm();
                itemFilm.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
                itemFilm.setJudul(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_JUDUL)));
                itemFilm.setTanggal(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_TANGGAL)));
                itemFilm.setDeskripsi(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_DEKSRIPSI)));
                itemFilm.setPoster(cursor.getString(cursor.getColumnIndex(DataContract.FilmFavEntry.COL_POSTER)));

                filmArrayList.add(itemFilm);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return filmArrayList;
    }

    public boolean isFilmFav(int filmId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        boolean isFavorite = false;

        try {
            Cursor cursor;
            String sql = "SELECT * FROM " + DataContract.FilmFavEntry.TABLE_FILM + " WHERE " + _ID + " = '" + filmId + "'";
            cursor = database.rawQuery(sql, null);
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isFavorite;
    }

    public void addFilmFav(ItemFilm itemFilm) {
        ContentValues values = new ContentValues();
        values.put(DataContract.FilmFavEntry._ID, itemFilm.getId());
        values.put(DataContract.FilmFavEntry.COL_JUDUL, itemFilm.getJudul());
        values.put(DataContract.FilmFavEntry.COL_TANGGAL, itemFilm.getTanggal());
        values.put(DataContract.FilmFavEntry.COL_DEKSRIPSI, itemFilm.getDeskripsi());
        values.put(DataContract.FilmFavEntry.COL_POSTER, itemFilm.getPoster());

        db.insert(DataContract.FilmFavEntry.TABLE_FILM, null, values);
    }

    public void deleteFilmFav(int filmId){
        db.delete(DataContract.FilmFavEntry.TABLE_FILM, _ID + " = '" + filmId + "'", null);
    }
}
