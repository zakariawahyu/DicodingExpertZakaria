package com.zakariawahyu.favkatalogfilm.fragment;


import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.zakariawahyu.favkatalogfilm.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] titleTab;


    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        titleTab = context.getResources().getStringArray(R.array.title_tab);
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) return new FilmFavFragment();
        else return  new TvShowsFavFragment();
    }

    @Override
    public int getCount() {
        return titleTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleTab[position];
    }

}
