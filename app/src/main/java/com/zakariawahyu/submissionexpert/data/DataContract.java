package com.zakariawahyu.submissionexpert.data;

import android.provider.BaseColumns;

public class DataContract {

   public static final class FilmFavEntry implements BaseColumns {

       public static String TABLE_FILM = "film";
       public static String COL_JUDUL = "judul";
       public static String COL_TANGGAL = "tanggal";
       public static String COL_DEKSRIPSI = "deskripsi";
       public static String COL_POSTER = "poster";

   }

   public static final class TvShowsFavEntry implements BaseColumns{

       public static String TABLE_TVSHOWS = "tvshows";
       public static String COL_JUDUL = "judul";
       public static String COL_TANGGAL = "tanggal";
       public static String COL_DEKSRIPSI = "deskripsi";
       public static String COL_POSTER = "poster";

   }
}
