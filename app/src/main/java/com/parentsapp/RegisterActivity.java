package com.parentsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parentsapp.models.User;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    FirebaseFirestore db;

    EditText et_parent_name, et_student_name, et_email, et_password;
    String uid, str_parent_name, str_student_name, str_email, str_password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button btn_regi;
    ImageButton btn_back;
    TextView gotologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait.");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btn_back = findViewById(R.id.back_btn);
        et_parent_name = findViewById(R.id.et_parent_name);
        et_student_name = findViewById(R.id.et_student_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_regi = findViewById(R.id.register_btn);
        gotologin = findViewById(R.id.gotologin);

        et_parent_name.addTextChangedListener(TextWatcher);
        et_student_name.addTextChangedListener(TextWatcher);
        et_email.addTextChangedListener(TextWatcher);
        et_password.addTextChangedListener(TextWatcher);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                //create user
                auth.createUserWithEmailAndPassword(str_email, str_password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Try again later",
                                            Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    FirebaseUser curUser = auth.getInstance().getCurrentUser();
                                    if (curUser != null)
                                        uid = curUser.getUid();
                                    User user = new User(
                                            uid,
                                            str_parent_name,
                                            str_student_name,
                                            str_email
                                    );
                                    db.collection("Users")
                                            .document(uid)
                                            .set(user);
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                    dialog.dismiss();
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
            str_parent_name = et_parent_name.getText().toString().trim();
            str_student_name = et_student_name.getText().toString().trim();
            str_email = et_email.getText().toString().trim();
            str_password = et_password.getText().toString().trim();

            if (!str_parent_name.isEmpty() && !str_student_name.isEmpty() && !str_email.isEmpty() && str_email.matches(emailPattern) && !str_password.isEmpty() && str_password.length() > 7)
                btn_regi.setEnabled(true);
            else
                btn_regi.setEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
