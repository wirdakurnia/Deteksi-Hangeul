package com.wirnin.hanguldetection.Activity;

import android.os.Bundle;

import com.wirnin.hanguldetection.Fragment.FragmentKata;
import com.wirnin.hanguldetection.Fragment.FragmentKonsonan;
import com.wirnin.hanguldetection.Fragment.FragmentVokal;
import com.wirnin.hanguldetection.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MateriActivity extends AppCompatActivity {
    private String KEY_JENIS = "JENIS";
    private String jenis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihan_huruf);

        Bundle extras = getIntent().getExtras();
        jenis = extras.getString(KEY_JENIS);

        assert jenis != null;
        if(jenis.equals("vokal")){
            FragmentVokal vokal = new FragmentVokal();
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            FT.replace(R.id.isi_jenis, vokal);
            FT.commit();
        }else if(jenis.equals("konsonan")){
            FragmentKonsonan konsonan = new FragmentKonsonan();
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            FT.replace(R.id.isi_jenis, konsonan);
            FT.commit();
        }else{
            FragmentKata kata = new FragmentKata();
            FragmentManager FM = getSupportFragmentManager();
            FragmentTransaction FT = FM.beginTransaction();
            FT.replace(R.id.isi_jenis, kata);
            FT.commit();
        }
    }
}
