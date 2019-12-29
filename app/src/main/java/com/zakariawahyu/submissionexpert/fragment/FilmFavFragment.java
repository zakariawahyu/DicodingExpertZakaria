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
import com.zakariawahyu.submissionexpert.data.DataHelper;
import com.zakariawahyu.submissionexpert.data.FilmHelper;
import com.zakariawahyu.submissionexpert.film.AdapterFilm;
import com.zakariawahyu.submissionexpert.film.AdapterFilmFav;
import com.zakariawahyu.submissionexpert.film.ItemFilm;
import com.zakariawahyu.submissionexpert.film.LoadFilmCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.zakariawahyu.submissionexpert.data.DataContract.FilmFavEntry.CONTENT_URI;
import static com.zakariawahyu.submissionexpert.helper.MappingHelper.mapCursorFilmToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFavFragment extends Fragment implements LoadFilmCallback {

    private RecyclerView recyclerView;
    private ArrayList<ItemFilm> list = new ArrayList<>();
    private ProgressBar progressBar;
    private AdapterFilmFav adapter;
    private FilmHelper filmHelper;
    private TextView emptyData;

    private static final String TAG = FilmFavFragment.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";


    public FilmFavFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film_fav, container, false);

        recyclerView = view.findViewById(R.id.rcFavFilm);
        progressBar = view.findViewById(R.id.progressBarFilmFav);
        emptyData = view.findViewById(R.id.tv_data_empty);
        emptyData.setText(getString(R.string.empty_favorites));

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        filmHelper = FilmHelper.getInstance(getContext());
        filmHelper.open();


        adapter = new AdapterFilmFav(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        DataObserver observer = new DataObserver(handler, getContext());
        Objects.requireNonNull(getActivity()).getContentResolver().registerContentObserver(CONTENT_URI, true, observer);

        if (savedInstanceState == null) {
            new LoadFilmAsync(getContext(), this).execute();
        } else {
            ArrayList<ItemFilm> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListFilm(list);
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
            }
        });
    }

    @Override
    public void postExecute(Cursor cursor) {
        progressBar.setVisibility(View.GONE);
        ArrayList<ItemFilm> itemFilms = mapCursorFilmToArrayList(cursor);

        if (itemFilms != null) {
            if (itemFilms.size() != 0) {
                adapter.setListFilm(itemFilms);
                progressBar.setVisibility(View.GONE);
                emptyData.setVisibility(View.GONE);
                Log.d(TAG, "postExecute: Ada data");
            } else {
                emptyData.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                adapter.setListFilm(new ArrayList<ItemFilm>());
                Log.d(TAG, "postExecute: Data null");
            }
        } else {
            progressBar.setVisibility(View.GONE);
            emptyData.setVisibility(View.VISIBLE);
            adapter.setListFilm(new ArrayList<ItemFilm>());
            Log.d(TAG, "postExecute: Data null");
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

    private static class LoadFilmAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFilmCallback> weakCallback;

        private LoadFilmAsync(Context context, LoadFilmCallback callback) {
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
            return weakContext.get().getContentResolver().query(CONTENT_URI,null,null,null,null);
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
