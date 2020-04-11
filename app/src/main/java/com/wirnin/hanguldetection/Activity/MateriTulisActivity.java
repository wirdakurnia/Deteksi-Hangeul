package com.wirnin.hanguldetection.Activity;

import android.os.Bundle;


import com.wirnin.hanguldetection.Fragment.FragmentKonsonan;
import com.wirnin.hanguldetection.Fragment.FragmentVokal;
import com.wirnin.hanguldetection.R;

import androidx.appcompat.app.AppCompatActivity;
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
