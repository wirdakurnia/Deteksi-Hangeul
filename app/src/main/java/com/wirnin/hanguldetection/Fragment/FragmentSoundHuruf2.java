package com.wirnin.hanguldetection.Fragment;


import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
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
import com.wirnin.hanguldetection.HangulClassifier;
import com.wirnin.hanguldetection.HangulTranslator;
import com.wirnin.hanguldetection.PaintView;
import com.wirnin.hanguldetection.R;

import java.io.IOException;
import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSoundHuruf2 extends Fragment {
    public static String KEY_FRG = "msg_fragment";
    public static String KEY_HURUF = "jenis";

    TextView txtHangeul;
    ImageButton btnBack;
    Query query;
    String hangeul;

    private static final String LABEL_FILE = "40-huruf.txt";
    private static final String MODEL_FILE = "optimized_hangul_tensorflow.pb";

    private HangulClassifier classifier;
    private PaintView paintView;
    private Button alt1, alt2, alt3, alt4;
    private LinearLayout altLayout;
    private EditText resultText;
    private TextView translationText;
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

        View rootview = inflater.inflate(R.layout.fragment_sound_huruf2, container, false);

        paintView = rootview.findViewById(R.id.paintView);

        TextView drawHereText = (TextView) rootview.findViewById(R.id.drawHere);
        paintView.setDrawText(drawHereText);

        Button clearButton = (Button) rootview.findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        Button classifyButton = (Button) rootview.findViewById(R.id.buttonClassify);
        classifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classify();
                paintView.reset();
                paintView.invalidate();
            }
        });

        Button backspaceButton = (Button) rootview.findViewById(R.id.buttonBackspace);
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backspace();
                altLayout.setVisibility(View.INVISIBLE);
                paintView.reset();
                paintView.invalidate();
            }
        });

        Button spaceButton = (Button) rootview.findViewById(R.id.buttonSpace);
        spaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                space();
            }
        });

        Button submitButton = (Button) rootview.findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                altLayout.setVisibility(View.INVISIBLE);
                translate();
            }
        });

        altLayout = (LinearLayout) rootview.findViewById(R.id.altLayout);
        altLayout.setVisibility(View.INVISIBLE);

//        alt1 = (Button) rootview.findViewById(R.id.alt1);
//        alt1.setOnClickListener(this);
//        alt2 = (Button) findViewById(R.id.alt2);
//        alt2.setOnClickListener(this);
//        alt3 = (Button) findViewById(R.id.alt3);
//        alt3.setOnClickListener(this);
//        alt4 = (Button) findViewById(R.id.alt4);
//        alt4.setOnClickListener(this);

        translationText = (TextView) rootview.findViewById(R.id.translationText);
        resultText = (EditText) rootview.findViewById(R.id.editText);

        loadModel();

        txtHangeul = rootview.findViewById(R.id.txtHangeul);
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
                        }
                        txtHangeul.setText(hangeul);

                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadFragment(new FragmentVokal2());
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
                        }
                        txtHangeul.setText(hangeul);
                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                loadFragment(new FragmentKonsonan2());
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonClear:
                clear();
                break;
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
            case R.id.buttonSpace:
                space();
                break;
            case R.id.buttonSubmit:
                altLayout.setVisibility(View.INVISIBLE);
                translate();
                break;
            case R.id.alt1:
            case R.id.alt2:
            case R.id.alt3:
            case R.id.alt4:
                useAltLabel(Integer.parseInt(view.getTag().toString()));
                break;
        }
    }

    private void useAltLabel(int index) {
        backspace();
        resultText.append(currentTopLabels[index]);
    }

    private void translate() {
        String text = resultText.getText().toString();
        if (text.isEmpty()) {
            return;
        }

        HashMap<String, String> postData = new HashMap<>();
        postData.put("text", text);
        postData.put("source", "ko");
        postData.put("target", "en");
        String apikey = getResources().getString(R.string.apikey);
        String url = getResources().getString(R.string.url);
        HangulTranslator translator = new HangulTranslator(postData, translationText, apikey, url);
        translator.execute();
    }

    private void space() {
        resultText.append(" ");
    }

    private void backspace() {
        int len = resultText.getText().length();
        if (len > 0) {
            resultText.getText().delete(len - 1, len);
        }
    }

    private void classify() {
        float pixels[] = paintView.getPixelData();
        currentTopLabels = classifier.classify(pixels);
        resultText.append(currentTopLabels[0]);
        altLayout.setVisibility(View.VISIBLE);
        alt1.setText(currentTopLabels[1]);
        alt2.setText(currentTopLabels[2]);
        alt3.setText(currentTopLabels[3]);
        alt4.setText(currentTopLabels[4]);
    }

    private void clear() {
        paintView.reset();
        paintView.invalidate();
        resultText.setText("");
        translationText.setText("");
        altLayout.setVisibility(View.INVISIBLE);
    }

    private void loadModel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = HangulClassifier.create(getAccess(),
                            MODEL_FILE, LABEL_FILE, PaintView.FEED_DIMENSION,
                            "input", "keep_prob", "output");
                } catch (final Exception e) {
                    throw new RuntimeException("Error loading pre-trained model.", e);
                }
            }

            private AssetManager getAccess() {
                throw new RuntimeException("Stub!");
            }
        }).start();
    }

}
