package com.wirnin.hanguldetection.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wirnin.hanguldetection.R;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LatihanActivity extends AppCompatActivity {
//    private TextView questionView;
    //private ImageButton nextButton;
    private Button  pilihan1, pilihan2, pilihan3;
    private ImageView imgs;
    Dialog myDialog;
    ImageView img_jawaban, icon_next;
    TextView txtclose, text_jawaban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihan);

        myDialog = new Dialog(this);

//        questionView = findViewById(R.id.textView);
        //nextButton = findViewById(R.id.buttonNext);
        imgs =  findViewById(R.id.imageView);

        pilihan1 = findViewById(R.id.pilihan1);
        pilihan2 = findViewById(R.id.pilihan2);
        pilihan3 = findViewById(R.id.pilihan3);

        displayQuestion();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.drawable.homekecil);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backhome = new Intent(getApplicationContext(), MenuUtama.class);
                        startActivity(backhome);
                    }
                }
        );


//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                displayQuestion();
//            }
//        });
    }


    private void displayQuestion() {
        final Query questionToDisplay = FirebaseDatabase.getInstance()
                .getReference().child("kata");

        questionToDisplay.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Random random = new Random();

                    int index = random.nextInt((int) dataSnapshot.getChildrenCount());
                    int count = 0;
                    for (DataSnapshot question : dataSnapshot.getChildren()) {
                        if (count == index) {

                            final SoalLatihan soalLatihan = question.getValue(SoalLatihan.class);

                            pilihan1.setText(soalLatihan.getPilihan1());
                            pilihan2.setText(soalLatihan.getPilihan2());
                            pilihan3.setText(soalLatihan.getPilihan3());

                            Picasso.get().load(soalLatihan.getGambar()).into(imgs);


                            pilihan1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(pilihan1.getText().toString().equals(soalLatihan.getHangeul())){
                                        showPopupBenar();
                                    }else{
                                        showPopupSalah();
                                    }
                                }
                            });

                            pilihan2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(pilihan2.getText().toString().equals(soalLatihan.getHangeul())){
                                        showPopupBenar();
                                    }else{
                                        showPopupSalah();
                                    }
                                }
                            });

                            pilihan3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(pilihan3.getText().toString().equals(soalLatihan.getHangeul())){
                                        showPopupBenar();
                                    }else{
                                        showPopupSalah();
                                    }
                                }
                            });

                            return;

                        } count += 1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ItemDetail", "onCancelled", databaseError.toException());
            }
        });
    }

    public void showPopupBenar() {
        myDialog.setContentView(R.layout.popup);
        txtclose = myDialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        icon_next = myDialog.findViewById(R.id.icon_next);
        icon_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayQuestion();
                myDialog.dismiss();
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
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
