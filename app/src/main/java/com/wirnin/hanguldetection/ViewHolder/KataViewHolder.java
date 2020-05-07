package com.wirnin.hanguldetection.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wirnin.hanguldetection.Kata;
import com.wirnin.hanguldetection.R;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class KataViewHolder extends RecyclerView.ViewHolder {
    public TextView tvHangeul, tvArti;
    public ImageView imgs;
    public ConstraintLayout ln;

    public KataViewHolder(@NonNull View itemView) {
        super(itemView);
        tvHangeul = itemView.findViewById(R.id.txt_hangeul);
        tvArti = itemView.findViewById(R.id.txt_arti);
        imgs = itemView.findViewById(R.id.img_kata);
        ln = itemView.findViewById(R.id.ln);
    }

    public void bindToVokal(Kata kata, View.OnClickListener onClickListener){
        tvHangeul.setText(kata.hangeul);
        tvArti.setText(kata.arti);
        Picasso.get().load(kata.gambar).into(imgs);
        ln.setOnClickListener(onClickListener);
    }
}
