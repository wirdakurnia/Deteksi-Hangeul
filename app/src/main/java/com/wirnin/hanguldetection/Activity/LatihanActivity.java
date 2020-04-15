package com.wirnin.hanguldetection.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private ImageButton nextButton;
    private Button  pilihan1, pilihan2, pilihan3;
    private ImageView imgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latihan);

//        questionView = findViewById(R.id.textView);
        nextButton = findViewById(R.id.buttonNext);
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


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayQuestion();
            }
        });
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
                                        Toast.makeText(LatihanActivity.this, "Benar", Toast.LENGTH_SHORT).show();
                                        displayQuestion();
                                    }else{
                                        Toast.makeText(LatihanActivity.this, "Salah", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            pilihan2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(pilihan2.getText().toString().equals(soalLatihan.getHangeul())){
                                        Toast.makeText(LatihanActivity.this, "Benar", Toast.LENGTH_SHORT).show();
                                        displayQuestion();
                                    }else{
                                        Toast.makeText(LatihanActivity.this, "Salah", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            pilihan3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(pilihan3.getText().toString().equals(soalLatihan.getHangeul())){
                                        Toast.makeText(LatihanActivity.this, "Benar", Toast.LENGTH_SHORT).show();
                                        displayQuestion();
                                    }else{
                                        Toast.makeText(LatihanActivity.this, "Salah", Toast.LENGTH_SHORT).show();
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



}
