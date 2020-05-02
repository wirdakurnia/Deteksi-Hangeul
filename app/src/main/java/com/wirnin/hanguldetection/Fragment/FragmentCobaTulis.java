package com.wirnin.hanguldetection.Fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wirnin.hanguldetection.HangulClassifier;
import com.wirnin.hanguldetection.PaintView;
import com.wirnin.hanguldetection.R;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCobaTulis extends Fragment implements View.OnClickListener{

    private static final String LABEL_FILE = "40-huruf.txt";
    private static final String MODEL_FILE = "optimized_hangul_tensorflow.pb";

    private HangulClassifier classifier;
    private PaintView paintView;
    private Button alt1, alt2, alt3, alt4;
    private LinearLayout altLayout;
    private EditText resultText;
    private TextView translationText;
    private String[] currentTopLabels;


    public FragmentCobaTulis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_coba_tulis, container, false);

        paintView = rootview.findViewById(R.id.paintView);

        TextView drawHereText = rootview.findViewById(R.id.drawHere);
        paintView.setDrawText(drawHereText);

        Button clearButton = rootview.findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(this);

        Button classifyButton = rootview.findViewById(R.id.buttonClassify);
        classifyButton.setOnClickListener(this);

        Button backspaceButton = rootview.findViewById(R.id.buttonBackspace);
        backspaceButton.setOnClickListener(this);

        Button spaceButton = rootview.findViewById(R.id.buttonSpace);
        spaceButton.setOnClickListener(this);

        Button submitButton = rootview.findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(this);

        altLayout = rootview.findViewById(R.id.altLayout);
        altLayout.setVisibility(View.INVISIBLE);

        alt1 = rootview.findViewById(R.id.alt1);
        alt1.setOnClickListener(this);
        alt2 = rootview.findViewById(R.id.alt2);
        alt2.setOnClickListener(this);
        alt3 = rootview.findViewById(R.id.alt3);
        alt3.setOnClickListener(this);
        alt4 = rootview.findViewById(R.id.alt4);
        alt4.setOnClickListener(this);

        translationText = rootview.findViewById(R.id.translationText);
        resultText = rootview.findViewById(R.id.editText);

        loadModel();

        return rootview;
    }

    /**
     * This method is called when the user clicks a button in the view.
     * @param view
     */
    @Override
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
                //translate();
                break;
            case R.id.alt1:
            case R.id.alt2:
            case R.id.alt3:
            case R.id.alt4:
                useAltLabel(Integer.parseInt(view.getTag().toString()));
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
     * Add a space to the text input.
     */
    private void space() {
        resultText.append(" ");
    }

    /**
     * Clear the text and drawing to return to the beginning state.
     */
    private void clear() {
        paintView.reset();
        paintView.invalidate();
        resultText.setText("");
        translationText.setText("");
        altLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * Perform the classification, updating UI elements with the results.
     */
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
