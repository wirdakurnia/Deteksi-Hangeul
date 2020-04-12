package com.wirnin.hanguldetection.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.wirnin.hanguldetection.R;

import androidx.appcompat.app.AppCompatActivity;

public class MenuBaca extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.backtohome,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.backHome:
                Intent backhome = new Intent(getApplicationContext(), MenuUtama.class);

                startActivity(backhome);

                return true;

        }
        return false;
    }

    String jenis;
    private String KEY_JENIS = "JENIS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_baca);
    }

    public void keVokal(View view) {
        jenis = "vokal";
        Intent kevokal = new Intent(this, MateriBacaActivity.class);
        kevokal.putExtra(KEY_JENIS, jenis);
        startActivity(kevokal);
    }

    public void keKonsonan(View view) {
        jenis = "konsonan";
        Intent kekonsonan = new Intent(this, MateriBacaActivity.class);
        kekonsonan.putExtra(KEY_JENIS, jenis);
        startActivity(kekonsonan);
    }

    public void keKata(View view) {
        jenis = "kata";
        Intent kekata = new Intent(this, MateriBacaActivity.class);
        kekata.putExtra(KEY_JENIS, jenis);
        startActivity(kekata);
    }
}
