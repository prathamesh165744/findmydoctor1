package com.example.findmydoctor1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_patient_register extends AppCompatActivity {

    private EditText patientName, patientEmail, patientPhone, patientCity, patientPassword;
    private Button registerPatient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        patientName = findViewById(R.id.patient_name);
        patientEmail = findViewById(R.id.patient_email);
        patientPhone = findViewById(R.id.patient_phone);
        patientCity = findViewById(R.id.patient_city);
        patientPassword = findViewById(R.id.patient_password);
        registerPatient = findViewById(R.id.register_patient);

        // Set click listener for the register button
        registerPatient.setOnClickListener(v -> registerPatient());
    }

    private void registerPatient() {
        // Get input values
        String name = patientName.getText().toString().trim();
        String email = patientEmail.getText().toString().trim();
        String phone = patientPhone.getText().toString().trim();
        String city = patientCity.getText().toString().trim();
        String password = patientPassword.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name)) {
            patientName.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            patientEmail.setError("Valid email is required");
            return;
        }
        if (TextUtils.isEmpty(phone) || phone.length() < 10) {
            patientPhone.setError("Valid phone number is required");
            return;
        }
        if (TextUtils.isEmpty(city)) {
            patientCity.setError("City is required");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            patientPassword.setError("Password must be at least 6 characters");
            return;
        }

        // Create user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        Toast.makeText(activity_patient_register.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity_patient_register.this, activity_patient_login.class));
                        finish();
                    } else {
                        // Registration failed
                        Toast.makeText(activity_patient_register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
