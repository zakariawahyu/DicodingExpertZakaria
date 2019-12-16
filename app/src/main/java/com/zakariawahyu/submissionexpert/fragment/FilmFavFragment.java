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
import com.zakariawahyu.submissionexpert.data.DataHelper;
import com.zakariawahyu.submissionexpert.data.FilmHelper;
import com.zakariawahyu.submissionexpert.film.AdapterFilm;
import com.zakariawahyu.submissionexpert.film.AdapterFilmFav;
import com.zakariawahyu.submissionexpert.film.ItemFilm;
import com.zakariawahyu.submissionexpert.film.LoadFilmCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFavFragment extends Fragment implements LoadFilmCallback {

    private RecyclerView recyclerView;
    private ArrayList<ItemFilm> list = new ArrayList<>();
    private ProgressBar progressBar;
    private AdapterFilmFav adapter;
    private FilmHelper filmHelper;

    private static final String TAG = FilmFavFragment.class.getSimpleName();
    private static final String EXTRA_STATE = "EXTRA_STATE";


    public FilmFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film_fav, container, false);

        recyclerView = view.findViewById(R.id.rcFavFilm);
        progressBar = view.findViewById(R.id.progressBarFilmFav);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setHasFixedSize(true);

        filmHelper = FilmHelper.getInstance(getContext());
        filmHelper.open();


        adapter = new AdapterFilmFav(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (savedInstanceState == null) {
            new LoadFilmAsync(filmHelper, this).execute();
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
                Log.d(TAG, "run: PreExecute");
            }
        });
    }

    @Override
    public void postExecute(ArrayList<ItemFilm> itemFilms) {
        progressBar.setVisibility(View.GONE);
        if (itemFilms.size() != 0) {
            adapter.setListFilm(itemFilms);
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

    private static class LoadFilmAsync extends AsyncTask<Void, Void, ArrayList<ItemFilm>> {

        private final WeakReference<FilmHelper> weakDataHelper;
        private final WeakReference<LoadFilmCallback> weakCallback;

        private LoadFilmAsync(FilmHelper dataHelper, LoadFilmCallback callback) {
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
        protected ArrayList<ItemFilm> doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: ");
            return weakDataHelper.get().getAllFilmFav();
        }

        @Override
        protected void onPostExecute(ArrayList<ItemFilm> itemFilms) {
            super.onPostExecute(itemFilms);
            weakCallback.get().postExecute(itemFilms);
            Log.d(TAG, "onPostExecute: ");
        }
    }

}
