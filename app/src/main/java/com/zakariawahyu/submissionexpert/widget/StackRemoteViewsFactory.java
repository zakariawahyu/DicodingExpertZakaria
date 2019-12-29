package com.zakariawahyu.submissionexpert.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.data.DataContract;
import com.zakariawahyu.submissionexpert.data.DataHelper;
import com.zakariawahyu.submissionexpert.film.ItemFilm;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<ItemFilm> mWidgetItemsFilm = new ArrayList<>();

    private final Context mContext;
    private Cursor cursor;

    private final static String TAG = "WIDGET_DATA";

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        dataBanner();
    }

    private void dataBanner(){
        mWidgetItemsFilm.clear();

        DataHelper helper = new DataHelper(mContext);
        SQLiteDatabase database = helper.getWritableDatabase();

        cursor = database.query(DataContract.FilmFavEntry.TABLE_FILM, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ItemFilm itemFilm = new ItemFilm(cursor);
                mWidgetItemsFilm.add(itemFilm);
                Log.i(TAG, "loadWidgetData: " + itemFilm.getJudul());
                Log.i(TAG, "loadWidgetData: " + itemFilm.getPoster());
                Log.i(TAG, "loadWidgetData: " + mWidgetItemsFilm.size());
            } while (cursor.moveToNext());
        }

        cursor = database.query(DataContract.TvShowsFavEntry.TABLE_TVSHOWS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ItemFilm itemFilm = new ItemFilm(cursor);
                mWidgetItemsFilm.add(itemFilm);

                Log.i(TAG, "loadWidgetData: " + itemFilm.getJudul());
                Log.i(TAG, "loadWidgetData: " + itemFilm.getPoster());
                Log.i(TAG, "loadWidgetData: " + mWidgetItemsFilm.size());
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItemsFilm.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        if (mWidgetItemsFilm.size() != 0) {
            String judul = mWidgetItemsFilm.get(position).getJudul();
            String poster = mWidgetItemsFilm.get(position).getPoster();
            String des = mWidgetItemsFilm.get(position).getDeskripsi();
            String urlPoster = "https://image.tmdb.org/t/p/w185" + poster;
            try {

                rv.setTextViewText(R.id.tv_name, judul);
                rv.setTextViewText(R.id.tvitemDeskripsiwidget, des);
                Bitmap p = Glide.with(mContext)
                        .asBitmap()
                        .load(urlPoster)
                        .apply(new RequestOptions().fitCenter())
                        .submit()
                        .get();
                rv.setImageViewBitmap(R.id.img_widget_poster, p);
            } catch (Exception e) {
                Log.d(TAG, "error");
            }

            Bundle extras = new Bundle();
            extras.putString(WidgetFavorit.EXTRA_NAME, judul);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);


            rv.setOnClickFillInIntent(R.id.relativ, fillInIntent);
        }
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
