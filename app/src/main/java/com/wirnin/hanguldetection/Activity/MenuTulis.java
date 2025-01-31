package com.wirnin.hanguldetection.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wirnin.hanguldetection.R;

public class MenuTulis extends AppCompatActivity {
    String jenis;
    private String KEY_JENIS = "JENIS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tulis);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Jenis Materi Tulis");
        toolbar.setNavigationIcon(R.drawable.homekecil);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backhome = new Intent(getApplicationContext(), MenuUtama.class);
                        startActivity(backhome);
                    }
                }
        );
    }
    public void keVokal(View view) {
        jenis = "vokal";
        Intent kevokal2 = new Intent(this, MateriTulisActivity.class);
        kevokal2.putExtra(KEY_JENIS, jenis);
        startActivity(kevokal2);
    }

    public void keKonsonan(View view) {
        jenis = "konsonan";
        Intent kekonsonan2 = new Intent(this, MateriTulisActivity.class);
        kekonsonan2.putExtra(KEY_JENIS, jenis);
        startActivity(kekonsonan2);
    }
}
