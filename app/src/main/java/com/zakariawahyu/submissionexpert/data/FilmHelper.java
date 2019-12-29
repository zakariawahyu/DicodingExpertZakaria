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

    public Cursor queryByIdProviderFilm(String id) {
        return db.query(DataContract.FilmFavEntry.TABLE_FILM, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProviderFilm() {
        return db.query(DataContract.FilmFavEntry.TABLE_FILM
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProviderFilm(ContentValues values) {
        return db.insert(DataContract.FilmFavEntry.TABLE_FILM, null, values);
    }

    public int deleteProviderFilm(String id) {
        return db.delete(DataContract.FilmFavEntry.TABLE_FILM, _ID + " = ?", new String[]{id});
    }

}
