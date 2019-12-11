package com.zakariawahyu.submissionexpert.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.tvshows.AdapterTvShows;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<TvShowsItem> list = new ArrayList<>();
    private ProgressBar progressBar;
    private AdapterTvShows adapter;

    private TvShowsViewModel tvShowsViewModel;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);

        recyclerView = view.findViewById(R.id.rcTvShow);
        progressBar = view.findViewById(R.id.progressBar);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());

        adapter = new AdapterTvShows(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(lm);

        tvShowsViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(TvShowsViewModel.class);

        tvShowsViewModel.setTvShowsdata();
        showLoading(true);

        tvShowsViewModel.getTvShowsData().observe(this, new Observer<ArrayList<TvShowsItem>>() {
            @Override
            public void onChanged(ArrayList<TvShowsItem> itemTvShows) {
                if (itemTvShows != null) {
                    adapter.setData(itemTvShows);
                    showLoading(false);
                }
            }
        });

        return view;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
