package com.zakariawahyu.submissionexpert.tvshows;

import android.database.Cursor;


public interface LoadTvShowsCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
