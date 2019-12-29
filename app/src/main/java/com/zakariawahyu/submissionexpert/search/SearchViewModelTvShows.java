package com.zakariawahyu.submissionexpert.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;

import java.util.ArrayList;

public class SearchViewModelTvShows extends ViewModel implements SearchRepositoryCallbackTvShows {

    private MutableLiveData<ArrayList<TvShowsItem>> itemsLiveData = new MutableLiveData<>();

    public SearchViewModelTvShows() {

    }

    public void searchTvShows(String query){
        new SearchRepositoryTvShows(this, query);
    }


    @Override
    public void onPostExecute(ArrayList<TvShowsItem> tvShowsItems) {
        itemsLiveData.postValue(tvShowsItems);
    }

    public LiveData<ArrayList<TvShowsItem>> getItemsLiveData() {
        return itemsLiveData;
    }
}
