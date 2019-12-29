package com.zakariawahyu.submissionexpert.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zakariawahyu.submissionexpert.ReminderSetting;
import com.zakariawahyu.submissionexpert.search.Search;
import com.zakariawahyu.submissionexpert.SettingLanguage;
import com.zakariawahyu.submissionexpert.film.AdapterFilm;
import com.zakariawahyu.submissionexpert.film.FilmViewModel;
import com.zakariawahyu.submissionexpert.film.ItemFilm;
import com.zakariawahyu.submissionexpert.R;

import java.util.ArrayList;

import static com.zakariawahyu.submissionexpert.search.Search.EXTRA_TYPE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FilmFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<ItemFilm> list = new ArrayList<>();
    private ProgressBar progressBar;
    private AdapterFilm adapter;

    private FilmViewModel filmViewModel;

    public FilmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_film, container, false);

        recyclerView = view.findViewById(R.id.rcFilm);
        progressBar = view.findViewById(R.id.progressBar);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());

        adapter = new AdapterFilm(getActivity(), list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(lm);

        filmViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FilmViewModel.class);

        filmViewModel.setFilmdata();
        showLoading(true);



        filmViewModel.getFilmdata().observe(this, new Observer<ArrayList<ItemFilm>>() {
            @Override
            public void onChanged(ArrayList<ItemFilm> itemFilms) {
                if (itemFilms != null) {
                    adapter.setData(itemFilms);
                    showLoading(false);
                } else {
                    Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setHasOptionsMenu(true);


        return view;
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_setting_bahasa:
                i = new Intent(getActivity(), SettingLanguage.class);
                startActivity(i);
                break;
            case R.id.action_search:
                Intent intent = new Intent(getActivity(), Search.class);
                intent.putExtra(EXTRA_TYPE, getString(R.string.film));
                startActivity(intent);
                break;
            case R.id.action_setting_prefpengguna:
                i = new Intent(getActivity(), ReminderSetting.class);
                startActivity(i);
                break;
        }
        return true;
    }

}
