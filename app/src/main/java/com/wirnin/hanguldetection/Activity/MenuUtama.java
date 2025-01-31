package com.wirnin.hanguldetection.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wirnin.hanguldetection.R;

import androidx.appcompat.app.AppCompatActivity;

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

    public void keMenuPercakapan(View view) {
        Intent menuCakap = new Intent(getApplicationContext(), MenuPercakapan.class);
        startActivity(menuCakap);
    }

    public void keMenuPenyusunan(View view) {
        Intent menuPenyusunan = new Intent(getApplicationContext(), PenyusunanSukuKata.class);
        startActivity(menuPenyusunan);
    }
}
