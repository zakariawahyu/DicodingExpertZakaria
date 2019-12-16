package com.zakariawahyu.submissionexpert.tvshows;

import com.zakariawahyu.submissionexpert.film.ItemFilm;

import java.util.ArrayList;

public interface LoadTvShowsCallback {
    void preExecute();
    void postExecute(ArrayList<TvShowsItem> tvShowsItems);
}
