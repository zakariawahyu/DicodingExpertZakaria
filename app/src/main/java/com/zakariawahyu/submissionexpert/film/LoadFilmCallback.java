package com.zakariawahyu.submissionexpert.film;

import android.database.Cursor;


public interface LoadFilmCallback {
    void preExecute();
    void postExecute(Cursor cursor);
}
