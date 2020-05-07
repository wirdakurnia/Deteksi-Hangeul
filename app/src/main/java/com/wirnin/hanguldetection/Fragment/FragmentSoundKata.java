package com.wirnin.hanguldetection.Fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wirnin.hanguldetection.Activity.MenuUtama;
import com.wirnin.hanguldetection.R;

import java.io.IOException;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSoundKata extends Fragment {
    public static String KEY_FRG = "msg_fragment";
    public static String KEY_LATIHAN = "jenislatihan";

    TextView txtHangeul, txtArti, txtLafal;
    ImageButton btnSound, btnBack;
    ImageView imgKata;
    Query query;
    String hangeul, arti, lafal, sound, gambar;

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


    public FragmentSoundKata() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_sound_kata, container, false);

        final Toolbar toolbar = rootview.findViewById(R.id.toolbar);

        final Bundle data = new Bundle();
        String jenisLatihan = "baca";
        data.putString(FragmentSoundKata.KEY_LATIHAN, jenisLatihan);

        txtHangeul = rootview.findViewById(R.id.txtRomanzi);
        txtArti = rootview.findViewById(R.id.txtArti);
        txtLafal = rootview.findViewById(R.id.txtLafal);
        imgKata = rootview.findViewById(R.id.img_kata);
        btnSound = rootview.findViewById(R.id.btnSound);
        btnBack = rootview.findViewById(R.id.buttonBack);

        //assert getArguments() != null;
        String key = getArguments().getString(KEY_FRG);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        query = reference.child("kata").orderByChild("id").equalTo(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot katas : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        hangeul = katas.child("hangeul").getValue().toString();
                        arti = katas.child("arti").getValue().toString();
                        lafal = "(" + katas.child("lafal").getValue().toString() + ")";
                        gambar = katas.child("gambar").getValue().toString();
                        sound = katas.child("suara").getValue().toString();
                    }
                    txtHangeul.setText(hangeul);
                    txtArti.setText(arti);
                    txtLafal.setText(lafal);
                    Picasso.get().load(gambar).into(imgKata);

                    toolbar.setTitle("Kata "+'"'+arti+'"');

                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //loadFragment(new FragmentKata());
                            FragmentKata kata = new FragmentKata();
                            kata.setArguments(data);
                            FragmentManager FM = getFragmentManager();
                            FragmentTransaction FT = FM.beginTransaction();
                            FT.replace(R.id.frameLayout, kata);
                            FT.commit();
                        }
                    });

                    btnSound.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            try {
                                mediaPlayer.setDataSource(sound);

                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mediaPlayer) {
                                        mediaPlayer.start();
                                    }
                                });
                                mediaPlayer.prepare();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Inflate the layout for this fragment
        return rootview;
    }

}
