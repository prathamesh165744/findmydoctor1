package com.example.findmydoctor1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_patient_login extends AppCompatActivity {

    private EditText patientEmail, patientPassword;
    private Button loginButton;
    private TextView forgotPassword, registerText;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        patientEmail = findViewById(R.id.patient_email);
        patientPassword = findViewById(R.id.patient_password);
        loginButton = findViewById(R.id.patient_login_button);
        forgotPassword = findViewById(R.id.patient_forgot_password);
        registerText = findViewById(R.id.patient_register_text);

        // Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");

        // Login Button Click
        loginButton.setOnClickListener(v -> patientLogin());

        // Forgot Password Click
        forgotPassword.setOnClickListener(v -> {
            String email = patientEmail.getText().toString().trim();
            if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                resetPassword(email);
            } else {
                Toast.makeText(this, "Enter a valid email to reset password", Toast.LENGTH_SHORT).show();
            }
        });

        // Redirect to Registration Page
        registerText.setOnClickListener(v -> {
            startActivity(new Intent(activity_patient_login.this, activity_patient_register.class));
        });
    }

    private void patientLogin() {
        String email = patientEmail.getText().toString().trim();
        String password = patientPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            patientEmail.setError("Enter a valid email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            patientPassword.setError("Enter password");
            return;
        }

        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(activity_patient_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity_patient_login.this, activity_patient_dashbord.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(activity_patient_login.this, "Authentication Failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(activity_patient_login.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity_patient_login.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
