package com.wirnin.hanguldetection.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wirnin.hanguldetection.Huruf;
import com.wirnin.hanguldetection.R;

public class SoundViewHolder extends RecyclerView.ViewHolder {
    TextView txtHangeul, txtRomanzi, txtLafal;
    Button btnSound;

    public SoundViewHolder(@NonNull View itemView) {
        super(itemView);
        txtHangeul = itemView.findViewById(R.id.txtRomanzi);
        txtRomanzi = itemView.findViewById(R.id.txtRomanzi);
        txtLafal = itemView.findViewById(R.id.txtLafal);
    }

    public void bindToSound(Huruf huruf){
        txtHangeul.setText(huruf.hangeul);
        txtRomanzi.setText(huruf.huruf);
        txtLafal.setText(huruf.lafal);
    }
}
