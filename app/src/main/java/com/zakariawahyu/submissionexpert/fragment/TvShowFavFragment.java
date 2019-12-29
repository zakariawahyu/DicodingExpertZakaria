package com.zakariawahyu.submissionexpert.fragment;


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

import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.data.TvShowsHelper;
import com.zakariawahyu.submissionexpert.tvshows.AdapterTvShowsFav;
import com.zakariawahyu.submissionexpert.tvshows.LoadTvShowsCallback;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.zakariawahyu.submissionexpert.data.DataContract.TvShowsFavEntry.CONTENT_URI;
import static com.zakariawahyu.submissionexpert.helper.MappingHelper.mapCursorTvShowToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFavFragment extends Fragment implements LoadTvShowsCallback {

    private RecyclerView recyclerView;
    private ArrayList<TvShowsItem> list = new ArrayList<>();
    private ProgressBar progressBar;
    private AdapterTvShowsFav adapter;
    private TvShowsHelper filmHelper;
    private TextView emptyData;

    private static final String TAG = FilmFavFragment.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public TvShowFavFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show_fav, container, false);

        recyclerView = view.findViewById(R.id.rcFavTvShows);
        progressBar = view.findViewById(R.id.progressBarTvShowsFav);
        emptyData = view.findViewById(R.id.tv_data_empty);
        emptyData.setText(getString(R.string.empty_favorites));

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        filmHelper = TvShowsHelper.getInstance(getContext());
        filmHelper.open();

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver observer = new DataObserver(handler, getContext());
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI, true, observer);



        adapter = new AdapterTvShowsFav(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (savedInstanceState == null) {
            new LoadTvShowsAsync(getContext(), this).execute();
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
                emptyData.setVisibility(View.GONE);
                Log.d(TAG, "run: PreExecute");
            }
        });
    }

    @Override
    public void postExecute(Cursor cursor) {
        progressBar.setVisibility(View.GONE);
        ArrayList<TvShowsItem> tvShowsItems = mapCursorTvShowToArrayList(cursor);
        if (tvShowsItems != null) {
            if (tvShowsItems.size() != 0) {
                adapter.setListTvShows(tvShowsItems);
                emptyData.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "postExecute: ");
            } else {
                emptyData.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                adapter.setListTvShows(new ArrayList<TvShowsItem>());
                Log.d(TAG, "postExecute: data null");
            }
        } else {
            emptyData.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            adapter.setListTvShows(new ArrayList<TvShowsItem>());
            Log.d(TAG, " Data null");
        }
        Log.d(TAG, "postExecute");
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

    private static class LoadTvShowsAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTvShowsCallback> weakCallback;

        private LoadTvShowsAsync(Context context, LoadTvShowsCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
            Log.d(TAG, "onPreExecute: ");
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

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }
}
