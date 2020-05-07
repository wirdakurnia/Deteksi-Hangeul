package com.wirnin.hanguldetection.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.wirnin.hanguldetection.Kalimat;
import com.wirnin.hanguldetection.R;

public class KalimatViewHolder extends RecyclerView.ViewHolder {
    public TextView tvArti;
    public ConstraintLayout ln;

    public KalimatViewHolder(@NonNull View itemView) {
        super(itemView);
        tvArti = itemView.findViewById(R.id.tv_arti);
        ln = itemView.findViewById(R.id.ln);
    }

    public void bindToKalimat(Kalimat kalimat, View.OnClickListener onClickListener){
        tvArti.setText(kalimat.arti);
        ln.setOnClickListener(onClickListener);
    }
}
