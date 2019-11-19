package com.zakariawahyu.submissionexpert.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.film.AdapterFilm;
import com.zakariawahyu.submissionexpert.film.DataFilm;
import com.zakariawahyu.submissionexpert.film.ItemFilm;
import com.zakariawahyu.submissionexpert.tvshows.AdapterTvShows;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsData;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<TvShowsItem> list = new ArrayList<>();

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        recyclerView = view.findViewById(R.id.rcTvShow);
        list.addAll(TvShowsData.getList());

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());

        AdapterTvShows adapter = new AdapterTvShows(getActivity(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(lm);

        return view;
    }

}
