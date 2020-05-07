package com.wirnin.hanguldetection.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wirnin.hanguldetection.Fragment.FragmentKalimat;
import com.wirnin.hanguldetection.Fragment.FragmentKata;
import com.wirnin.hanguldetection.R;

public class MenuPercakapan extends AppCompatActivity {
    String jenis;
    private String KEY_JENIS = "JENIS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_percakapan);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Percakapan Dasar");
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
        FragmentKalimat kalimat = new FragmentKalimat();
        FragmentManager FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.isi_kalimat, kalimat);
        FT.commit();
    }
}
