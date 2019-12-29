package com.zakariawahyu.submissionexpert.tvshows;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zakariawahyu.submissionexpert.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowsViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TvShowsItem>> listTvShows = new MutableLiveData<>();

    public void setTvShowsdata() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShowsItem> listItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+ BuildConfig.TMDB_API_KEY +"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvshows = list.getJSONObject(i);
                        TvShowsItem itemTvShows = new TvShowsItem(tvshows);
                        listItems.add(itemTvShows);
                    }
                    listTvShows.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShowsItem>> getTvShowsData() {
        return listTvShows;
    }


}
