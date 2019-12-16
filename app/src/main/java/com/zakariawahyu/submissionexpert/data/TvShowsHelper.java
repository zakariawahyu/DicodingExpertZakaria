package com.zakariawahyu.submissionexpert.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class TvShowsHelper {

    private static DataHelper dbHelper;
    private static TvShowsHelper INSTANCE;

    private static SQLiteDatabase db;

    private TvShowsHelper(Context context) {
        dbHelper = new DataHelper(context);
    }

    public static TvShowsHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvShowsHelper(context);
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

    public void addTvShowsFav(TvShowsItem tvShowsItem) {
        ContentValues values = new ContentValues();
        values.put(DataContract.FilmFavEntry._ID, tvShowsItem.getId());
        values.put(DataContract.FilmFavEntry.COL_JUDUL, tvShowsItem.getJudul());
        values.put(DataContract.FilmFavEntry.COL_TANGGAL, tvShowsItem.getTanggal());
        values.put(DataContract.FilmFavEntry.COL_DEKSRIPSI, tvShowsItem.getDeskripsi());
        values.put(DataContract.FilmFavEntry.COL_POSTER, tvShowsItem.getPoster());

        db.insert(DataContract.TvShowsFavEntry.TABLE_TVSHOWS, null, values);
    }



    public void deleteTvShowsFav(int tvshowsId){
        db.delete(DataContract.TvShowsFavEntry.TABLE_TVSHOWS, _ID + " = '" + tvshowsId + "'", null);
    }

    public ArrayList<TvShowsItem> getAllTvShowsFav() {
        ArrayList<TvShowsItem> tvShowsItemArrayList = new ArrayList<>();
        Cursor cursor = db.query(DataContract.TvShowsFavEntry.TABLE_TVSHOWS,
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
                TvShowsItem tvShowsItem = new TvShowsItem();
                tvShowsItem.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
                tvShowsItem.setJudul(cursor.getString(cursor.getColumnIndex(DataContract.TvShowsFavEntry.COL_JUDUL)));
                tvShowsItem.setTanggal(cursor.getString(cursor.getColumnIndex(DataContract.TvShowsFavEntry.COL_TANGGAL)));
                tvShowsItem.setDeskripsi(cursor.getString(cursor.getColumnIndex(DataContract.TvShowsFavEntry.COL_DEKSRIPSI)));
                tvShowsItem.setPoster(cursor.getString(cursor.getColumnIndex(DataContract.TvShowsFavEntry.COL_POSTER)));

                tvShowsItemArrayList.add(tvShowsItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return tvShowsItemArrayList;
    }


    public boolean isTvShowsFav(int tvshowsId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        boolean isFavorite = false;

        try {
            Cursor cursor;
            String sql = "SELECT * FROM " + DataContract.TvShowsFavEntry.TABLE_TVSHOWS + " WHERE " + _ID + " = '" + tvshowsId + "'";
            cursor = database.rawQuery(sql, null);
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isFavorite;
    }
}
