package com.zakariawahyu.submissionexpert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    private ArrayList<ItemFilm> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.listView_film);
        list.addAll(DataFilm.getList());

        AdapterFilm adapter = new AdapterFilm(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemFilm itemFilm = list.get(position);
                Intent intent = new Intent(MainActivity.this, DetailFilm.class);
                intent.putExtra("Film",itemFilm );
                startActivity(intent);
            }
        });
    }
}
