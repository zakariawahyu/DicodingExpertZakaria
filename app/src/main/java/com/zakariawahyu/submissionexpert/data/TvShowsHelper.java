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

    public Cursor queryByIdProviderTvShows(String id) {
        return db.query(DataContract.TvShowsFavEntry.TABLE_TVSHOWS, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProviderTvShows() {
        return db.query(DataContract.TvShowsFavEntry.TABLE_TVSHOWS
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProviderTvShows(ContentValues values) {
        return db.insert(DataContract.TvShowsFavEntry.TABLE_TVSHOWS, null, values);
    }

    public int deleteProviderTvShows(String idTvShows) {
        return db.delete(DataContract.TvShowsFavEntry.TABLE_TVSHOWS, _ID + " = ?", new String[]{idTvShows});
    }

}
