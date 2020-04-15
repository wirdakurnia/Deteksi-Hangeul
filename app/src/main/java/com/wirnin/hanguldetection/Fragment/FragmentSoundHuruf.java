package com.wirnin.hanguldetection.Fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
public class FragmentSoundHuruf extends Fragment {
    public static String KEY_FRG = "msg_fragment";
    public static String KEY_HURUF = "jenis";

    TextView txtHangeul, txtRomanzi, txtLafal;
    ImageButton btnSound, btnBack;
    Query query;
    String hangeul, romanzi, lafal, sound;

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_sound_huruf, container, false);

        Toolbar toolbar = rootview.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.homekecil);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backhome = new Intent(getActivity(), MenuUtama.class);
                        startActivity(backhome);
                    }
                }
        );

        txtHangeul = rootview.findViewById(R.id.txtHangeul);
        txtRomanzi = rootview.findViewById(R.id.txtRomanzi);
        txtLafal = rootview.findViewById(R.id.txtLafal);
        btnSound = rootview.findViewById(R.id.btnSound);
        btnBack = rootview.findViewById(R.id.buttonBack);

        //assert getArguments() != null;
        String key = getArguments().getString(KEY_FRG);
        String jenishuruf = getArguments().getString(KEY_HURUF);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        if(jenishuruf.equals("vokal")){
            query = reference.child("vokal").orderByChild("id").equalTo(key);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0
                        for (DataSnapshot vokals : dataSnapshot.getChildren()) {
                            // do something with the individual "issues"
                            hangeul = vokals.child("hangeul").getValue().toString();
                            romanzi = vokals.child("huruf").getValue().toString();
                            lafal = "(" + vokals.child("lafal").getValue().toString() + ")";
                            sound = vokals.child("suara").getValue().toString();
                        }
                        txtHangeul.setText(hangeul);
                        txtRomanzi.setText(romanzi);
                        txtLafal.setText(lafal);

                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadFragment(new FragmentVokal());
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

        }else if(jenishuruf.equals("konsonan")){
            query = reference.child("konsonan").orderByChild("id").equalTo(key);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0
                        for (DataSnapshot konsonans : dataSnapshot.getChildren()) {
                            // do something with the individual "issues"
                            hangeul = konsonans.child("hangeul").getValue().toString();
                            romanzi = konsonans.child("huruf").getValue().toString();
                            lafal = "(" + konsonans.child("lafal").getValue().toString() + ")";
                            sound = konsonans.child("suara").getValue().toString();
                        }
                        txtHangeul.setText(hangeul);
                        txtRomanzi.setText(romanzi);
                        txtLafal.setText(lafal);
                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadFragment(new FragmentKonsonan());
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
        }else {
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
        }

        return rootview;
    }

}
