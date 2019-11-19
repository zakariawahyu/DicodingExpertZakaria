package com.zakariawahyu.submissionexpert;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;

import java.util.Locale;

import io.paperdb.Paper;

public class Setting extends AppCompatActivity {

    private RadioButton indonesia, english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        indonesia = findViewById(R.id.radioButtonId);
        english = findViewById(R.id.radioButtonEng);

        String getBhasa = Locale.getDefault().getDisplayLanguage();

        switch (getBhasa){
            case "Indonesia" :
                indonesia.setChecked(true);
                english.setChecked(false);
                english.setEnabled(false);
                break;

            case "English" :
                indonesia.setChecked(false);
                indonesia.setEnabled(false);
                english.setChecked(true);
        }

    }

    public void simpanBahasa(View view){
        Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(intent);
        finish();
    }
}
