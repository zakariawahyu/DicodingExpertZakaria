package com.zakariawahyu.favkatalogfilm.fragment;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zakariawahyu.favkatalogfilm.LoadCallback;
import com.zakariawahyu.favkatalogfilm.R;
import com.zakariawahyu.favkatalogfilm.adapter.AdapterTvShowsFav;
import com.zakariawahyu.favkatalogfilm.model.TvShowsItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.zakariawahyu.favkatalogfilm.database.DataContract.TvShowsFavEntry.CONTENT_URI;
import static com.zakariawahyu.favkatalogfilm.helper.MappingHelper.mapCursorTvShowToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowsFavFragment extends Fragment implements LoadCallback {

    private static final String TAG = TvShowsFavFragment.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";

    private ProgressBar progressBar;
    private TextView tvNoData;
    private ArrayList<TvShowsItem> list = new ArrayList<>();
    private AdapterTvShowsFav adapter;


    public TvShowsFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_shows_fav, container, false);

        progressBar = view.findViewById(R.id.progressBarTvShowsFav);
        tvNoData = view.findViewById(R.id.tv_data_empty);
        RecyclerView recyclerView = view.findViewById(R.id.rcFavTvShows);
        tvNoData.setText(getString(R.string.empty_favorites));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new AdapterTvShowsFav(getContext(), list);
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver myObserver = new DataObserver(handler, getContext());
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        if (savedInstanceState == null) {
            new LoadAsync(getContext(), this).execute();
        } else {
            ArrayList<TvShowsItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListTvShows(list);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListData());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void postExecute(Cursor cursor) {
        progressBar.setVisibility(View.GONE);
        ArrayList<TvShowsItem> tvShowsItems = mapCursorTvShowToArrayList(cursor);
        if (tvShowsItems != null) {
            if (tvShowsItems.size() != 0) {
                adapter.setListTvShows(tvShowsItems);
                tvNoData.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "postExecute: ");
            } else {
                tvNoData.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                adapter.setListTvShows(new ArrayList<TvShowsItem>());
                Log.d(TAG, "postExecute: data null");
            }
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            adapter.setListTvShows(new ArrayList<TvShowsItem>());
            Log.d(TAG, " Data null");
        }
        Log.d(TAG, "postExecute");
    }

    private static class LoadAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadCallback> weakCallback;

        private LoadAsync(Context context, LoadCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            return weakContext.get().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
            Log.d(TAG, "onPostExecute: ");
        }
    }

    public static class DataObserver extends ContentObserver {
        final Context context;

        DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}
