package com.wirnin.hanguldetection.Fragment;


import android.content.Intent;
import android.media.MediaPlayer;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.wirnin.hanguldetection.Activity.LatihanActivity;
import com.wirnin.hanguldetection.Activity.MenuUtama;
import com.wirnin.hanguldetection.HangulClassifier;
import com.wirnin.hanguldetection.HangulTranslator;
import com.wirnin.hanguldetection.PaintView;
import com.wirnin.hanguldetection.R;

import java.io.IOException;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTulisHangul extends Fragment implements View.OnClickListener{
    public static String KEY_FRG = "msg_fragment";
    public static String KEY_HURUF = "jenis";
    private static final String LABEL_FILE = "40-huruf.txt";
    private static final String MODEL_FILE = "optimized_hangul_tensorflow.pb";

    TextView txtHangeul, drawHereText;
    ImageButton btnBack;
    Query query;
    String hangeul;
    DatabaseReference reference;
    Button classifyButton, backspaceButton, submitButton;

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

        String key = getArguments().getString(KEY_FRG);
        String jenishuruf = getArguments().getString(KEY_HURUF);

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

        submitButton = rootview.findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(this);

        altLayout = rootview.findViewById(R.id.altLayout);
        altLayout.setVisibility(View.INVISIBLE);

        resultText = rootview.findViewById(R.id.editText);

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
                        }
                        txtHangeul.setText(hangeul);

                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadFragment(new FragmentVokal());
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
                        }
                        txtHangeul.setText(hangeul);
                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadFragment(new FragmentKonsonan());
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
                break;
            case R.id.buttonBackspace:
                backspace();
                altLayout.setVisibility(View.INVISIBLE);
                paintView.reset();
                paintView.invalidate();
                break;
            case R.id.buttonSubmit:
                altLayout.setVisibility(View.INVISIBLE);
                translate();
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
    private void translate() {
        String text = resultText.getText().toString();

        if(text.equals(hangeul)){
            Toast.makeText(getActivity(), "Benar", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(), "Salah", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This function will switch out the last classified character with the alternative given the
     * index in the top labels array.
     */
    private void useAltLabel(int index) {
        backspace();
        resultText.append(currentTopLabels[index]);
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
