package com.wirnin.hanguldetection.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wirnin.hanguldetection.Huruf;
import com.wirnin.hanguldetection.R;

public class HurufViewHolder extends RecyclerView.ViewHolder{
    public TextView tvHangeul;
    public LinearLayout ln;

    public HurufViewHolder(@NonNull View itemView) {
        super(itemView);
        tvHangeul = itemView.findViewById(R.id.tv_hangeul);
        ln = itemView.findViewById(R.id.ln);
    }

    public void bindToVokal(Huruf huruf, View.OnClickListener onClickListener){
        tvHangeul.setText(huruf.hangeul);
        ln.setOnClickListener(onClickListener);
    }

}
