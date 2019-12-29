package com.zakariawahyu.submissionexpert.data;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DataContract {

    private static final String SCHEME = "content";
    public static final String AUTHORITY = "com.zakariawahyu.submissionexpert";

   public static final class FilmFavEntry implements BaseColumns {

       public static String TABLE_FILM = "film";
       public static String COL_JUDUL = "judul";
       public static String COL_TANGGAL = "tanggal";
       public static String COL_DEKSRIPSI = "deskripsi";
       public static String COL_POSTER = "poster";

       public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
               .authority(AUTHORITY)
               .appendPath(TABLE_FILM)
               .build();

   }

   public static final class TvShowsFavEntry implements BaseColumns{

       public static String TABLE_TVSHOWS = "tvshows";
       public static String COL_JUDUL = "judul";
       public static String COL_TANGGAL = "tanggal";
       public static String COL_DEKSRIPSI = "deskripsi";
       public static String COL_POSTER = "poster";

       public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
               .authority(AUTHORITY)
               .appendPath(TABLE_TVSHOWS)
               .build();

   }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
