package com.zakariawahyu.submissionexpert.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zakariawahyu.submissionexpert.R;
import com.zakariawahyu.submissionexpert.film.AdapterFilm;
import com.zakariawahyu.submissionexpert.film.ItemFilm;
import com.zakariawahyu.submissionexpert.tvshows.AdapterTvShows;
import com.zakariawahyu.submissionexpert.tvshows.TvShowsItem;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    public static final String EXTRA_QUERY = "extra_search";
    public static final String EXTRA_TYPE = "extra_type";

    private TextView tvNotFound;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private String itemType;
    private AdapterFilm adapter1;
    private AdapterTvShows adapter2;
    private SearchViewModelFilm searchViewModel1;
    private SearchViewModelTvShows searchViewModel2;
    private ArrayList<ItemFilm> list = new ArrayList<>();
    private ArrayList<TvShowsItem> list2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        itemType = getIntent().getStringExtra(EXTRA_TYPE);

        tvNotFound = findViewById(R.id.tv_no_items_match);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.rv_items);


        if (itemType.equals(getString(R.string.film))){
            adapter1 = new AdapterFilm(this, list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter1);

            searchViewModel1 = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModelFilm.class);
            searchViewModel1.getItemsLiveData().observe(this, getItems1);
        } else {
            adapter2 = new AdapterTvShows(this, list2);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter2);

            searchViewModel2 = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModelTvShows.class);
            searchViewModel2.getItemsLiveData().observe(this, getItems2);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchItem.expandActionView();
        searchView.setQuery(getIntent().getStringExtra(EXTRA_QUERY), true);
        searchView.setQueryHint("Search " + itemType);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showNotFound(false);


                query = query.toLowerCase().trim();
                if (itemType.equals(getString(R.string.film))){
                    adapter1.clearItems();
                    searchViewModel1.searchFilm(query);
                } else {
                    adapter2.clearItems();
                    searchViewModel2.searchTvShows(query);
                }
                showLoading(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                finish();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private Observer<ArrayList<ItemFilm>> getItems1 = new Observer<ArrayList<ItemFilm>>() {
        @Override
        public void onChanged(ArrayList<ItemFilm> items) {
            if (items.size() == 0) {
                showNotFound(true);
            }
            else {
                showNotFound(false);
                adapter1.setData(items);
                adapter1.notifyDataSetChanged();
            }
            showLoading(false);
        }
    };

    private Observer<ArrayList<TvShowsItem>> getItems2 = new Observer<ArrayList<TvShowsItem>>() {
        @Override
        public void onChanged(ArrayList<TvShowsItem> tvShowsItems) {
            if (tvShowsItems.size() == 0) {
                showNotFound(true);
            }
            else {
                showNotFound(false);
                adapter2.setData(tvShowsItems);
                adapter2.notifyDataSetChanged();
            }
            showLoading(false);
        }
    };



    private void showNotFound(boolean state) {
        if (state)
            tvNotFound.setVisibility(View.VISIBLE);
        else
            tvNotFound.setVisibility(View.GONE);
    }

    private void showLoading(boolean state) {
        if (state)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);
    }
}
