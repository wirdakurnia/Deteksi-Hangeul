package com.wirnin.hanguldetection.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.wirnin.hanguldetection.Fragment.FragmentKonsonan;
import com.wirnin.hanguldetection.Fragment.FragmentVokal;
import com.wirnin.hanguldetection.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MateriTulisActivity extends AppCompatActivity {
    private String KEY_JENIS = "JENIS";
    private String jenis;
    public static String KEY_LATIHAN = "jenislatihan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_tulis);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
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

        Bundle extras = getIntent().getExtras();
        jenis = extras.getString(KEY_JENIS);

        assert jenis != null;

        Bundle data = new Bundle();
        String jenisLatihan = "tulis";
        data.putString(MateriTulisActivity.KEY_LATIHAN, jenisLatihan);

        if(jenis.equals("vokal")){
            FragmentVokal vokal = new FragmentVokal();
            vokal.setArguments(data);
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            FT.replace(R.id.isi_materi_tulis, vokal);
            FT.commit();
        }else if(jenis.equals("konsonan")){
            FragmentKonsonan konsonan = new FragmentKonsonan();
            konsonan.setArguments(data);
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            FT.replace(R.id.isi_materi_tulis, konsonan);
            FT.commit();
        }
    }
}
