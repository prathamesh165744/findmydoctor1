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

public class activity_doctor_register extends AppCompatActivity {

    private EditText doctorName, doctorEmail, doctorPhone, doctorPassword;
    private Button registerDoctor;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        doctorName = findViewById(R.id.doctor_name);
        doctorEmail = findViewById(R.id.doctor_email);
        doctorPhone = findViewById(R.id.doctor_phone);
        doctorPassword = findViewById(R.id.doctor_password); // Ensure this field exists in your XML
        registerDoctor = findViewById(R.id.register_doctor);

        // Set click listener for the register button
        registerDoctor.setOnClickListener(v -> registerDoctor());
    }

    private void registerDoctor() {
        // Get input values
        String name = doctorName.getText().toString().trim();
        String email = doctorEmail.getText().toString().trim();
        String phone = doctorPhone.getText().toString().trim();
        String password = doctorPassword.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name)) {
            doctorName.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            doctorEmail.setError("Valid email is required");
            return;
        }
        if (TextUtils.isEmpty(phone) || phone.length() < 10) {
            doctorPhone.setError("Valid phone number is required");
            return;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            doctorPassword.setError("Password must be at least 6 characters");
            return;
        }

        // Create user with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Registration successful
                        Toast.makeText(activity_doctor_register.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(activity_doctor_register.this, activity_doctor_login.class));
                        finish();
                    } else {
                        // Registration failed
                        Toast.makeText(activity_doctor_register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
