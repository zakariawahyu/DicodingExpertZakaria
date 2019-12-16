package com.zakariawahyu.submissionexpert.fragment;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.data.TvShowsHelper;
import com.zakariawahyu.submissionexpert.tvshows.AdapterTvShowsFav;
import com.zakariawahyu.submissionexpert.tvshows.LoadTvShowsCallback;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment implements LoadTvShowsCallback {

    private RecyclerView recyclerView;
    private ArrayList<TvShowsItem> list = new ArrayList<>();
    private ProgressBar progressBar;
    private AdapterTvShowsFav adapter;
    private TvShowsHelper filmHelper;

    private static final String TAG = FilmFavFragment.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public TvShowFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show_fav, container, false);

        recyclerView = view.findViewById(R.id.rcFavTvShows);
        progressBar = view.findViewById(R.id.progressBarTvShowsFav);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        filmHelper = TvShowsHelper.getInstance(getContext());
        filmHelper.open();


        adapter = new AdapterTvShowsFav(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (savedInstanceState == null) {
            new TvShowFavFragment.LoadTvShowsAsync(filmHelper, this).execute();
        } else {
            ArrayList<TvShowsItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTvShows(list);
            }
        }

        return view;
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
                Log.d(TAG, "run: PreExecute");
            }
        });
    }

    @Override
    public void postExecute(ArrayList<TvShowsItem> tvShowsItems) {
        progressBar.setVisibility(View.GONE);
        if (tvShowsItems.size() != 0) {
            adapter.setListTvShows(tvShowsItems);
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "postExecute: ");
        } else {
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "postExecute: data null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        filmHelper.close();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListData());
    }

    private static class LoadTvShowsAsync extends AsyncTask<Void, Void, ArrayList<TvShowsItem>> {

        private final WeakReference<TvShowsHelper> weakDataHelper;
        private final WeakReference<LoadTvShowsCallback> weakCallback;

        private LoadTvShowsAsync(TvShowsHelper dataHelper, LoadTvShowsCallback callback) {
            weakDataHelper = new WeakReference<>(dataHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
            Log.d(TAG, "onPreExecute: ");
        }

        @Override
        protected ArrayList<TvShowsItem> doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            return weakDataHelper.get().getAllTvShowsFav();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShowsItem> tvShowsItems) {
            super.onPostExecute(tvShowsItems);
            weakCallback.get().postExecute(tvShowsItems);
            Log.d(TAG, "onPostExecute: ");
        }
    }
}
