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
public class FragmentSoundKalimat extends Fragment {
    public static String KEY_FRG = "msg_fragment";

    TextView txtArti, txtFormal, txtInformal, txtLafalFormal, txtLafalInformal;
    ImageButton btnSoundFormal,btnSoundInformal, btnBack;
    Query query;
    String arti, formal, informal, lafalFormal, lafalInformal, soundFormal, soundInformal;

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_sound_kalimat, container, false);

        final Toolbar toolbar = rootview.findViewById(R.id.toolbar);

        txtArti = rootview.findViewById(R.id.txtArti);
        txtFormal = rootview.findViewById(R.id.txtFormal);
        txtInformal = rootview.findViewById(R.id.txtInformal);
        txtLafalFormal = rootview.findViewById(R.id.txtLafalFormal);
        txtLafalInformal = rootview.findViewById(R.id.txtLafalInformal);
        btnSoundFormal = rootview.findViewById(R.id.btnSoundFormal);
        btnSoundInformal = rootview.findViewById(R.id.btnSoundInformal);
        btnBack = rootview.findViewById(R.id.buttonBack);

        //assert getArguments() != null;
        String key = getArguments().getString(KEY_FRG);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        query = reference.child("kalimat").orderByChild("id").equalTo(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0
                    for (DataSnapshot kalimats : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        arti = kalimats.child("arti").getValue().toString();
                        formal = kalimats.child("formal").getValue().toString();
                        informal = kalimats.child("informal").getValue().toString();
                        lafalFormal = "(" + kalimats.child("lafalformal").getValue().toString() + ")";
                        lafalInformal = "(" + kalimats.child("lafalinformal").getValue().toString() + ")";
                        soundFormal = kalimats.child("suaraformal").getValue().toString();
                        soundInformal = kalimats.child("suarainformal").getValue().toString();
                    }
                    txtArti.setText(arti);
                    txtFormal.setText(formal);
                    txtInformal.setText(informal);
                    txtLafalFormal.setText(lafalFormal);
                    txtLafalInformal.setText(lafalInformal);

                    toolbar.setTitle("Percakapan Dasar");

                    btnBack.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //loadFragment(new FragmentVokal());
                            FragmentKalimat kalimat = new FragmentKalimat();
                            FragmentManager FM = getFragmentManager();
                            FragmentTransaction FT = FM.beginTransaction();
                            FT.replace(R.id.frameLayout, kalimat);
                            FT.commit();
                        }
                    });

                    btnSoundFormal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            try {
                                mediaPlayer.setDataSource(soundFormal);

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

                    btnSoundInformal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            try {
                                mediaPlayer.setDataSource(soundInformal);

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

//        if(jenishuruf.equals("kalimat")){
//            query = reference.child("kalimat").orderByChild("id").equalTo(key);
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        // dataSnapshot is the "issue" node with all children with id 0
//                        for (DataSnapshot kalimats : dataSnapshot.getChildren()) {
//                            // do something with the individual "issues"
//                            arti = kalimats.child("arti").getValue().toString();
//                            formal = kalimats.child("formal").getValue().toString();
//                            informal = kalimats.child("informal").getValue().toString();
//                            lafalFormal = "(" + kalimats.child("lafalformal").getValue().toString() + ")";
//                            lafalInformal = "(" + kalimats.child("lafalinformal").getValue().toString() + ")";
//                            soundFormal = kalimats.child("suaraformal").getValue().toString();
//                            soundInformal = kalimats.child("suarainformal").getValue().toString();
//                        }
//                        txtArti.setText(arti);
//                        txtFormal.setText(formal);
//                        txtInformal.setText(informal);
//                        txtLafalFormal.setText(lafalFormal);
//                        txtLafalInformal.setText(lafalInformal);
//
//                        btnBack.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                //loadFragment(new FragmentVokal());
//                                FragmentKalimat kalimat = new FragmentKalimat();
//                                FragmentManager FM = getFragmentManager();
//                                FragmentTransaction FT = FM.beginTransaction();
//                                FT.replace(R.id.frameLayout, kalimat);
//                                FT.commit();
//                            }
//                        });
//
//                        btnSoundFormal.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                MediaPlayer mediaPlayer = new MediaPlayer();
//                                try {
//                                    mediaPlayer.setDataSource(soundFormal);
//
//                                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                        @Override
//                                        public void onPrepared(MediaPlayer mediaPlayer) {
//                                            mediaPlayer.start();
//                                        }
//                                    });
//                                    mediaPlayer.prepare();
//                                }catch (IOException e){
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                        btnSoundInformal.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                MediaPlayer mediaPlayer = new MediaPlayer();
//                                try {
//                                    mediaPlayer.setDataSource(soundInformal);
//
//                                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                        @Override
//                                        public void onPrepared(MediaPlayer mediaPlayer) {
//                                            mediaPlayer.start();
//                                        }
//                                    });
//                                    mediaPlayer.prepare();
//                                }catch (IOException e){
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//
//        }else {
//            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
//        }

        return rootview;
    }

}
