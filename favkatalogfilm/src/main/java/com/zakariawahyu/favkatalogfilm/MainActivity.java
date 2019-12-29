package com.zakariawahyu.favkatalogfilm;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.zakariawahyu.favkatalogfilm.fragment.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);


        adapter = new ViewPagerAdapter(getSupportFragmentManager(), this );
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }
}
