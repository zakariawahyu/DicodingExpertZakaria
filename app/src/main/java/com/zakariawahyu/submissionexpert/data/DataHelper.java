package com.zakariawahyu.submissionexpert.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dicodingexpert.db";

    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_TABLE_FILM = "CREATE TABLE " + DataContract.FilmFavEntry.TABLE_FILM + " (" +
                DataContract.FilmFavEntry._ID + " INTEGER NOT NULL," +
                DataContract.FilmFavEntry.COL_JUDUL + " TEXT NOT NULL, " +
                DataContract.FilmFavEntry.COL_TANGGAL + " TEXT NOT NULL, " +
                DataContract.FilmFavEntry.COL_DEKSRIPSI + " TEXT NOT NULL, " +
                DataContract.FilmFavEntry.COL_POSTER + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_TABLE_FILM);

        final String SQL_CREATE_TABLE_TVSHOWS = "CREATE TABLE " + DataContract.TvShowsFavEntry.TABLE_TVSHOWS+ " (" +
                DataContract.TvShowsFavEntry._ID + " INTEGER NOT NULL," +
                DataContract.TvShowsFavEntry.COL_JUDUL + " TEXT NOT NULL, " +
                DataContract.TvShowsFavEntry.COL_TANGGAL + " TEXT NOT NULL, " +
                DataContract.TvShowsFavEntry.COL_DEKSRIPSI + " TEXT NOT NULL, " +
                DataContract.TvShowsFavEntry.COL_POSTER + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_TABLE_TVSHOWS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.FilmFavEntry.TABLE_FILM);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.TvShowsFavEntry.TABLE_TVSHOWS);
        onCreate(db);
    }

}
