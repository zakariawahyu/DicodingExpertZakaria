package com.zakariawahyu.submissionexpert.search;

import com.zakariawahyu.submissionexpert.film.ItemFilm;

import java.util.ArrayList;

public interface SearchRepositoryCallbackFilm {
	void onPostExecute(ArrayList<ItemFilm> items);
}
