package com.example.findmydoctor1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.card.MaterialCardView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Initialize Doctor and Patient cards
        MaterialCardView cardDoctor = findViewById(R.id.cardDoctor);
        MaterialCardView cardPatient = findViewById(R.id.cardPatient);


//        cardDoctor.setOnClickListener(v -> {
//            startActivity(new Intent(this, activity_doctor_login.class));
//
//        });
//
//        // Set click listener for Patient card
//        cardPatient.setOnClickListener(v -> {
//            startActivity(new Intent(this, activity_patient_login.class));
//
//        });
    }
}