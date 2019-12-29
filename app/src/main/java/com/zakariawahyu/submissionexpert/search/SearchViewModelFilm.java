package com.zakariawahyu.submissionexpert.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zakariawahyu.submissionexpert.film.ItemFilm;

import java.util.ArrayList;

public class SearchViewModelFilm extends ViewModel implements SearchRepositoryCallbackFilm {

	private MutableLiveData<ArrayList<ItemFilm>> itemsLiveData = new MutableLiveData<>();

	public SearchViewModelFilm() {

	}

	public void searchFilm(String query) {
		new SearchRepositoryFilm(this,  query);
	}

	@Override
	public void onPostExecute(ArrayList<ItemFilm> items) {
		itemsLiveData.postValue(items);
	}

	public LiveData<ArrayList<ItemFilm>> getItemsLiveData() {
		return itemsLiveData;
	}
}

