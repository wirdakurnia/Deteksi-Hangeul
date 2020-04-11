package com.wirnin.hanguldetection.Activity;

import android.os.Bundle;


import com.wirnin.hanguldetection.Fragment.FragmentKonsonan2;
import com.wirnin.hanguldetection.Fragment.FragmentVokal2;
import com.wirnin.hanguldetection.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MateriActivity2 extends AppCompatActivity {
    private String KEY_JENIS = "JENIS";
    private String jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihan_huruf2);

        Bundle extras = getIntent().getExtras();
        jenis = extras.getString(KEY_JENIS);

        assert jenis != null;
        if(jenis.equals("vokal")){
            FragmentVokal2 vokal = new FragmentVokal2();
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            FT.replace(R.id.isi_jenis2, vokal);
            FT.commit();
        }else if(jenis.equals("konsonan")){
            FragmentKonsonan2 konsonan = new FragmentKonsonan2();
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            FT.replace(R.id.isi_jenis2, konsonan);
            FT.commit();
        }
    }
}
