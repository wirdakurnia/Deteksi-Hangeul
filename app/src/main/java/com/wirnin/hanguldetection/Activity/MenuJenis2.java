package com.wirnin.hanguldetection.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wirnin.hanguldetection.R;

public class MenuJenis2 extends AppCompatActivity {
    String jenis;
    private String KEY_JENIS = "JENIS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_jenis2);
    }
    public void keVokal(View view) {
        jenis = "vokal";
        Intent kevokal2 = new Intent(this, MateriActivity2.class);
        kevokal2.putExtra(KEY_JENIS, jenis);
        startActivity(kevokal2);
    }

    public void keKonsonan(View view) {
        jenis = "konsonan";
        Intent kekonsonan2 = new Intent(this, MateriActivity2.class);
        kekonsonan2.putExtra(KEY_JENIS, jenis);
        startActivity(kekonsonan2);
    }
}
