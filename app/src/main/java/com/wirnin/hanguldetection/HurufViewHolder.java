package com.wirnin.hanguldetection;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
