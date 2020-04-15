package com.wirnin.hanguldetection.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.wirnin.hanguldetection.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuUtama extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_utama);
    }

    public void keMenuBaca(View view) {
        Intent menuBaca = new Intent(getApplicationContext(), MenuBaca.class);
        startActivity(menuBaca);
    }

    public void keMenuLatihan(View view) {
        Intent latihan = new Intent(getApplicationContext(), LatihanActivity.class);
        startActivity(latihan);
    }

    public void keMenuPenulisan(View view) {
        Intent menuNulis = new Intent(getApplicationContext(), MenuTulis.class);
        startActivity(menuNulis);
    }
}
