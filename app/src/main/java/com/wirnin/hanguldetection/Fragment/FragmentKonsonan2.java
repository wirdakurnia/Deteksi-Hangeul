package com.wirnin.hanguldetection.Fragment;


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
import com.wirnin.hanguldetection.Huruf;
import com.wirnin.hanguldetection.HurufViewHolder;
import com.wirnin.hanguldetection.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentKonsonan2 extends Fragment {
    public static String TAG = "FragmentKonsonan2";

    public static String KEY_FRG = "msg_fragment";
    public static String KEY_HURUF = "jenis";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Huruf, HurufViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    String vokalkey;


    public FragmentKonsonan2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_konsonan2, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecycler = rootview.findViewById(R.id.list_konsonan2);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(getContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // Set up FirebaseRecyclerAdapter with the Query
        Query query = getQuery(mDatabase);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Huruf>()
                .setQuery(query, Huruf.class)
                .build();


        mAdapter = new FirebaseRecyclerAdapter<Huruf, HurufViewHolder>(options) {
            @Override
            public HurufViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());

                return new HurufViewHolder(inflater.inflate(R.layout.item_huruf, parent, false));
            }
            @Override
            protected void onBindViewHolder(@NonNull HurufViewHolder holder, final int i, @NonNull final Huruf huruf) {
                holder.bindToVokal(huruf, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child("konsonan")
                                .orderByChild("hangeul")
                                .equalTo(huruf.hangeul)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                            vokalkey = childSnapshot.getKey();
                                        }
                                        assert vokalkey != null;
                                        Log.i(TAG, huruf.id);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        Bundle data = new Bundle();
                        String msg = huruf.id;
                        String jenis = "konsonan";
                        data.putString(FragmentKonsonan2.KEY_FRG, msg);
                        data.putString(FragmentKonsonan2.KEY_HURUF, jenis);

                        FragmentSoundHuruf2 sound = new FragmentSoundHuruf2();
                        sound.setArguments(data);

                        FragmentManager FM = getActivity().getSupportFragmentManager();
                        FragmentTransaction FT = FM.beginTransaction();
                        FT.replace(R.id.isi_jenis2, sound);
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
        Query query = mDatabase.child("konsonan");
        return query;
    }

}
