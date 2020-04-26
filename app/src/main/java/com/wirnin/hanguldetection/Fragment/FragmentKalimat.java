package com.wirnin.hanguldetection.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.wirnin.hanguldetection.Huruf;
import com.wirnin.hanguldetection.HurufViewHolder;
import com.wirnin.hanguldetection.Kalimat;
import com.wirnin.hanguldetection.KalimatViewHolder;
import com.wirnin.hanguldetection.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentKalimat extends Fragment {
    public static String TAG = "FragmentKalimat";

    public static String KEY_FRG = "msg_fragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Kalimat, KalimatViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    String vokalkey, jenisLatihan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_kalimat, container, false);

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

        mRecycler = rootview.findViewById(R.id.list_kalimat);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(getContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // Set up FirebaseRecyclerAdapter with the Query
        Query query = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Kalimat>()
                .setQuery(query, Kalimat.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<Kalimat, KalimatViewHolder>(options) {
            @Override
            public KalimatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                return new KalimatViewHolder(inflater.inflate(R.layout.item_kalimat, parent, false));
            }
            @Override
            protected void onBindViewHolder(@NonNull KalimatViewHolder holder, final int i, @NonNull final Kalimat kalimat) {
                holder.bindToKalimat(kalimat, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child("kalimat")
                                .orderByChild("arti")
                                .equalTo(kalimat.arti)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                            vokalkey = childSnapshot.getKey();
                                        }
                                        assert vokalkey != null;
                                        Log.i(TAG, kalimat.id);
                                    }

                                    @Override
                                     public void onCancelled(@NonNull DatabaseError databaseError) {

                                     }
                                });

                        Bundle data = new Bundle();
                        String msg = kalimat.id;

                        data.putString(FragmentKalimat.KEY_FRG, msg);

                        FragmentSoundKalimat soundKalimat = new FragmentSoundKalimat();
                        FragmentManager FM = getActivity().getSupportFragmentManager();
                        soundKalimat.setArguments(data);
                        FragmentTransaction FT = FM.beginTransaction();
                        FT.replace(R.id.isi_kalimat, soundKalimat);
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
        Query query = mDatabase.child("kalimat");
        return query;
    }

}
