package com.example.findmydoctor1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_doctor_login extends AppCompatActivity {

    private EditText email, password;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        // Initialize views
        email = findViewById(R.id.doctor_email);
        password = findViewById(R.id.doctor_password);
        loginButton = findViewById(R.id.doctor_login_button);

        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");

        // Set onClickListener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorLogin();
            }
        });

        // Go to Register page
        findViewById(R.id.button_register).setOnClickListener(v -> {
            Intent intent = new Intent(activity_doctor_login.this, activity_doctor_register.class);
            startActivity(intent);
        });
    }

    private void doctorLogin() {
        // Get email and password
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();

        // Check if email and password fields are empty
        if (emailStr.isEmpty() || passwordStr.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress dialog
        progressDialog.show();

        // Sign in with Firebase Authentication
        mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener(this, task -> {
                    // Dismiss progress dialog
                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        // If login is successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(activity_doctor_login.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Navigate to Doctor Dashboard
                        startActivity(new Intent(activity_doctor_login.this, DoctorDashboardActivity.class));
                        finish();
                    } else {
                        // If login fails
                        Toast.makeText(activity_doctor_login.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

