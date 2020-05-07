package com.wirnin.hanguldetection.Fragment;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wirnin.hanguldetection.Activity.MenuUtama;
import com.wirnin.hanguldetection.HangulClassifier;
import com.wirnin.hanguldetection.PaintView;
import com.wirnin.hanguldetection.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTulisHangul extends Fragment implements View.OnClickListener{
    public static String KEY_FRG = "msg_fragment";
    public static String KEY_HURUF = "jenis";
    public static String KEY_LATIHAN = "jenislatihan";
    private static final String LABEL_FILE = "40-huruf.txt";
    private static final String MODEL_FILE = "optimized_hangul_tensorflow.pb";

    TextView txtHangeul, drawHereText, txtclose, text_jawaban, txtHuruf;
    ImageButton btnBack;
    Query query;
    String hangeul, alur, huruf;
    DatabaseReference reference;
    Button classifyButton, backspaceButton;
    Dialog myDialog;
    ImageView img_jawaban, icon_next, img_alur;

    private HangulClassifier classifier;
    private PaintView paintView;
    private LinearLayout altLayout;
    private EditText resultText;
    private String[] currentTopLabels;

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_tulis_hangul, container, false);

        final Toolbar toolbar = rootview.findViewById(R.id.toolbar);

        final Bundle data = new Bundle();
        String jenisLatihan = "tulis";
        data.putString(FragmentTulisHangul.KEY_LATIHAN, jenisLatihan);

        String key = getArguments().getString(KEY_FRG);
        String jenishuruf = getArguments().getString(KEY_HURUF);

        myDialog = new Dialog(getContext());

        txtHangeul = rootview.findViewById(R.id.txtHangeul);
        btnBack = rootview.findViewById(R.id.buttonBack);

        reference = FirebaseDatabase.getInstance().getReference();

        paintView = rootview.findViewById(R.id.paintView);

        drawHereText = rootview.findViewById(R.id.drawHere);
        paintView.setDrawText(drawHereText);

        classifyButton = rootview.findViewById(R.id.buttonClassify);
        classifyButton.setOnClickListener(this);

        backspaceButton = rootview.findViewById(R.id.buttonBackspace);
        backspaceButton.setOnClickListener(this);

        altLayout = rootview.findViewById(R.id.altLayout);
        altLayout.setVisibility(View.INVISIBLE);

        resultText = rootview.findViewById(R.id.editText);

        img_alur = rootview.findViewById(R.id.img_alur);
        txtHuruf = rootview.findViewById(R.id.txtHuruf);

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
                            alur = vokals.child("alur").getValue().toString();
                            huruf = vokals.child("huruf").getValue().toString();
                        }
                        txtHangeul.setText(hangeul);
                        txtHuruf.setText("("+huruf+")");
                        Picasso.get().load(alur).into(img_alur);

                        toolbar.setTitle("Huruf "+hangeul);

                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //loadFragment(new FragmentVokal());
                                FragmentVokal vokal = new FragmentVokal();
                                vokal.setArguments(data);
                                FragmentManager FM = getFragmentManager();
                                FragmentTransaction FT = FM.beginTransaction();
                                FT.replace(R.id.frameLayout, vokal);
                                FT.commit();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else{
            query = reference.child("konsonan").orderByChild("id").equalTo(key);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // dataSnapshot is the "issue" node with all children with id 0
                        for (DataSnapshot konsonans : dataSnapshot.getChildren()) {
                            // do something with the individual "issues"
                            hangeul = konsonans.child("hangeul").getValue().toString();
                            alur = konsonans.child("alur").getValue().toString();
                            huruf = konsonans.child("huruf").getValue().toString();
                        }
                        txtHangeul.setText(hangeul);
                        txtHuruf.setText("("+huruf+")");
                        Picasso.get().load(alur).into(img_alur);

                        toolbar.setTitle("Huruf "+ hangeul);

                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //loadFragment(new FragmentKonsonan());
                                FragmentKonsonan konsonan = new FragmentKonsonan();
                                konsonan.setArguments(data);
                                FragmentManager FM = getFragmentManager();
                                FragmentTransaction FT = FM.beginTransaction();
                                FT.replace(R.id.frameLayout, konsonan);
                                FT.commit();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        loadModel();

        // Inflate the layout for this fragment
        return rootview;
    }

    /**
     * This method is called when the user clicks a button in the view.
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonClassify:
                classify();
                paintView.reset();
                paintView.invalidate();
                cekPenulisan();
                break;
            case R.id.buttonBackspace:
                resultText.setText("");
                altLayout.setVisibility(View.INVISIBLE);
                paintView.reset();
                paintView.invalidate();
                break;
        }
    }

    /**
     * Delete the last character in the text input field.
     */
    private void backspace() {
        int len = resultText.getText().length();
        if (len > 0) {
            resultText.getText().delete(len - 1, len);
        }
    }


    /**
     * Perform the classification, updating UI elements with the results.
     */
    private void classify() {
        float pixels[] = paintView.getPixelData();
        currentTopLabels = classifier.classify(pixels);
        resultText.append(currentTopLabels[0]);
        altLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Perform the translation using the current Korean text in the text input field.
     */
    private void cekPenulisan() {
        String text = resultText.getText().toString();

        if(text.equals(hangeul)){
            showPopupBenar();
        }else{
            showPopupSalah();
        }
    }

    @Override
    public void onResume() {
        paintView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        paintView.onPause();
        super.onPause();
    }

    public void showPopupBenar() {
        myDialog.setContentView(R.layout.popup);
        icon_next = myDialog.findViewById(R.id.icon_next);
        txtclose = myDialog.findViewById(R.id.txtclose);
        icon_next.setVisibility(View.INVISIBLE);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                backspace();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void showPopupSalah() {
        myDialog.setContentView(R.layout.popup);
        text_jawaban = myDialog.findViewById(R.id.text_jawaban);
        text_jawaban.setText("Jawaban Salah");
        img_jawaban = myDialog.findViewById(R.id.img_jawaban);
        img_jawaban.setImageResource(R.drawable.sadicon);
        icon_next = myDialog.findViewById(R.id.icon_next);
        icon_next.setVisibility(View.INVISIBLE);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                backspace();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    /**
     * Load pre-trained model in memory.
     */
    private void loadModel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = HangulClassifier.create(getActivity().getAssets(),
                            MODEL_FILE, LABEL_FILE, PaintView.FEED_DIMENSION,
                            "input", "keep_prob", "output");
                } catch (final Exception e) {
                    throw new RuntimeException("Error loading pre-trained model.", e);
                }
            }
        }).start();
    }

}
