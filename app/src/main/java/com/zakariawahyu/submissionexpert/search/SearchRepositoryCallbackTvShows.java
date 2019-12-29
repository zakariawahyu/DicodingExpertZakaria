package com.zakariawahyu.submissionexpert.search;

import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;

import java.util.ArrayList;

public interface SearchRepositoryCallbackTvShows {
    void onPostExecute(ArrayList<TvShowsItem> tvShowsItems);
}
