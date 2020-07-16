package com.example.chemez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_form extends AppCompatActivity {
    EditText email, pass;
    Button btn_login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        email = (EditText) findViewById(R.id.mailidLF);
        pass = (EditText) findViewById(R.id.passwordLF);
        btn_login = (Button) findViewById(R.id.loginbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                String passw = pass.getText().toString().trim();
                if (TextUtils.isEmpty(mail)) {
                    email.setError("Please Enter Your Email ID:");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(passw)) {
                    pass.setError("Please Enter Your password:");
                    pass.requestFocus();
                } else if (passw.length() < 6) {
                    pass.setError("Password Too short");
                    pass.requestFocus();
                }
                firebaseAuth.signInWithEmailAndPassword(mail, passw).addOnCompleteListener(login_form.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), Homescreen.class));
                        } else {
                            Toast.makeText(login_form.this, "Login Failed,Please Register to continue", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }

    public void btn_signupform(View view) {
        startActivity(new Intent(getApplicationContext(), signup_form.class));
    }

}