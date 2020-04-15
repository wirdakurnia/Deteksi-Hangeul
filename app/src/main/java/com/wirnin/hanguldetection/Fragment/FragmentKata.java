package com.wirnin.hanguldetection.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wirnin.hanguldetection.Activity.MenuUtama;
import com.wirnin.hanguldetection.Kata;
import com.wirnin.hanguldetection.KataViewHolder;
import com.wirnin.hanguldetection.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentKata extends Fragment {
    public static String TAG = "FragmentKata";

    public static String KEY_FRG = "msg_fragment";
    public static String KEY_HURUF = "jenis";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Kata, KataViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    String vokalkey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_kata, container, false);

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = rootview.findViewById(R.id.list_kata);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(getContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query query = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Kata>()
                .setQuery(query, Kata.class)
                .build();


        mAdapter = new FirebaseRecyclerAdapter<Kata, KataViewHolder>(options) {
            @Override
            public KataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                return new KataViewHolder(inflater.inflate(R.layout.item_kata, parent, false));
            }
            @Override
            protected void onBindViewHolder(@NonNull KataViewHolder holder, final int i, @NonNull final Kata kata) {
                holder.bindToVokal(kata, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child("kata")
                                .orderByChild("hangeul")
                                .equalTo(kata.hangeul)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                            vokalkey = childSnapshot.getKey();
                                        }
                                        assert vokalkey != null;
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        Bundle data = new Bundle();
                        String msg = kata.id;
                        data.putString(FragmentKonsonan.KEY_FRG, msg);

                        FragmentSoundKata sound = new FragmentSoundKata();
                        sound.setArguments(data);

                        FragmentManager FM = getActivity().getSupportFragmentManager();
                        FragmentTransaction FT = FM.beginTransaction();
                        FT.replace(R.id.isi_materi_baca, sound);
                        FT.commit();
                    }

                });


            }
        };

        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    private Query getQuery(DatabaseReference mDatabase){
        Query query = mDatabase.child("kata");
        return query;
    }

}
