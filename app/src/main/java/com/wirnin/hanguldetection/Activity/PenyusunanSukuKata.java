package com.wirnin.hanguldetection.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wirnin.hanguldetection.R;

public class PenyusunanSukuKata extends AppCompatActivity {

    TextView textKet1, textKet2, textKet3, isi_satu, isi_dua, isi_tiga, isi_empat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyusunan_sukukata);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Penyusunan Suku Kata");
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

        textKet1 = findViewById(R.id.textKet);
        textKet2 = findViewById(R.id.textKet2);
        textKet3 = findViewById(R.id.textKet3);

        isi_satu = findViewById(R.id.isi_satu);
        isi_dua = findViewById(R.id.isi_dua);
        isi_tiga = findViewById(R.id.isi_tiga);
        isi_empat = findViewById(R.id.isi_empat);

        textKet1.setText("K : huruf konsonan \t V : huruf vokal");
        textKet2.setText("Vokal yang garis panjangnya vertical\nmaka vokal ditulis disamping konsonan");
        textKet3.setText("Vokal yang garis panjangnya horizontal\nmaka vokal ditulis dibawah konsonan");

        isi_satu.setText("Contoh : \nㄱ + ㅏ = 가");
        isi_dua.setText("Contoh : \nㄱ + ㅡ = 그");
        isi_tiga.setText("Contoh : \nㅂ + ㅏ + ㄴ = 반");
        isi_empat.setText("Contoh : \nㅅ + ㅜ + ㄹ = 술");
    }
}
