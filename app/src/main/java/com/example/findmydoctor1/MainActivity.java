package com.example.findmydoctor1;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
    }
}



