package com.zakariawahyu.submissionexpert.film;

import java.util.ArrayList;

public interface LoadFilmCallback {
    void preExecute();
    void postExecute(ArrayList<ItemFilm> itemFilms);
}
