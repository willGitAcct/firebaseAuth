package com.example.firebase_attempt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    //activity to add password reset function
    private EditText email;
    private Button resetPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = (EditText) findViewById(R.id.email);
        resetPassword = (Button) findViewById(R.id.reset);

        auth = FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String emailText = email.getText().toString().trim();

        if (emailText.isEmpty()) {
            email.setError("Email required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            email.setError("Please provide a valid email.");
            email.requestFocus();
        }

        auth.sendPasswordResetEmail(emailText).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotPassword.this, "Password reset link set: check your email", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(forgotPassword.this, "Try again; error.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}