package com.zakariawahyu.submissionexpert.search;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.zakariawahyu.submissionexpert.BuildConfig;
import com.zakariawahyu.submissionexpert.film.ItemFilm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class SearchRepositoryFilm {

	public SearchRepositoryFilm(SearchRepositoryCallbackFilm searchViewModelCallback, String query) {
		final ArrayList<ItemFilm> itemsList = new ArrayList<>();
		final WeakReference<SearchRepositoryCallbackFilm> weakSearchViewModel = new WeakReference<>(searchViewModelCallback);
		String url = "https://api.themoviedb.org/3/search/movie"+ "?api_key=" + BuildConfig.TMDB_API_KEY  + "&language=en-US&query=" + query;
		AsyncHttpClient client = new AsyncHttpClient();

		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				try {
					String result = new String(responseBody);
					JSONObject responseObject = new JSONObject(result);
					JSONArray list = responseObject.getJSONArray("results");

					for (int i = 0; i < list.length(); ++i) {
						JSONObject itemData = list.getJSONObject(i);
						ItemFilm item = new ItemFilm(itemData);
						itemsList.add(item);
					}

					weakSearchViewModel.get().onPostExecute(itemsList);
				} catch (JSONException e) {
					Log.d("JSONException", Objects.requireNonNull(e.getMessage()));
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
				weakSearchViewModel.get().onPostExecute(new ArrayList<ItemFilm>());
			}
		});
	}
}
