package com.zakariawahyu.favkatalogfilm;

import android.database.Cursor;

public interface LoadCallback {
    void postExecute(Cursor cursor);
}

