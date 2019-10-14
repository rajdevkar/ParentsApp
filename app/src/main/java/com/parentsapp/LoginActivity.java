package com.parentsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    FirebaseFirestore db;

    TextView gotoregi;
    EditText et_email, et_password;
    String str_email, str_pass;
    Button btn_login;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait.");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        gotoregi = findViewById(R.id.gotoregi);

        et_email.addTextChangedListener(TextWatcher);
        et_password.addTextChangedListener(TextWatcher);

        gotoregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                auth.signInWithEmailAndPassword(str_email, str_pass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    dialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                                } else {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    dialog.dismiss();
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    private android.text.TextWatcher TextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            str_email = et_email.getText().toString().trim();
            str_pass = et_password.getText().toString().trim();

            if (!str_email.isEmpty() && str_email.matches(emailPattern) && !str_pass.isEmpty() && str_pass.length() > 7)
                btn_login.setEnabled(true);
            else
                btn_login.setEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
