package com.parentsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Splash extends AppCompatActivity {
    FirebaseUser curUser;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        curUser = auth.getCurrentUser();

        if (curUser != null) {
            startActivity(new Intent(Splash.this, MainActivity.class));
        } else {
            startActivity(new Intent(Splash.this, LoginActivity.class));
        }
        finish();
    }
}
