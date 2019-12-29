package com.zakariawahyu.submissionexpert.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.tabs.TabLayout;
import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.ReminderSetting;
import com.zakariawahyu.submissionexpert.SettingLanguage;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        tabLayout = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.view_pager);


        adapter = new ViewPagerAdapter(getChildFragmentManager(), getContext());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setElevation(0);
        if (actionBar != null){
            actionBar.setTitle(R.string.fav);
        }
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fav, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_setting_bahasa:
                i = new Intent(getActivity(), SettingLanguage.class);
                startActivity(i);
                break;
            case R.id.action_setting_prefpengguna:
                i = new Intent(getActivity(), ReminderSetting.class);
                startActivity(i);
                break;
        }
        return true;
    }

}
