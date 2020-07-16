package com.example.chemez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_form extends AppCompatActivity {
    EditText emailRF, collegeRF, passRF, cpassRF, nameRF;
    Button register_btn;
    TextView loginnow;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);
        firebaseAuth = FirebaseAuth.getInstance();
        emailRF = (EditText) findViewById(R.id.emailRF);
        nameRF = (EditText) findViewById(R.id.nameSF);
        loginnow = findViewById(R.id.loginRF);
        collegeRF = (EditText) findViewById(R.id.collegeRF);
        passRF = (EditText) findViewById(R.id.passRF);
        cpassRF = (EditText) findViewById(R.id.cpassRF);
        register_btn = (Button) findViewById(R.id.registerbtn);
        databaseReference = FirebaseDatabase.getInstance().getReference("student");
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailRF.getText().toString();
                final String pass = passRF.getText().toString();
                final String college = collegeRF.getText().toString();
                final String cpass = cpassRF.getText().toString();
                final String stname = nameRF.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    emailRF.setError("Please Enter Your Email ID:");
                    emailRF.requestFocus();
                } else if (TextUtils.isEmpty(pass)) {
                    passRF.setError("Please Enter Your password:");
                    passRF.requestFocus();
                } else if (TextUtils.isEmpty(college)) {
                    collegeRF.setError("Please Enter Your College name:");
                    collegeRF.requestFocus();
                } else if (TextUtils.isEmpty(cpass)) {
                    cpassRF.setError("Please Confirm your password:");
                    cpassRF.requestFocus();
                } else if (TextUtils.isEmpty(stname)) {
                    passRF.setError("Please Enter Your Name:");
                    passRF.requestFocus();
                } else if (!pass.equals(cpass)) {
                    passRF.setError("Passwords Do Not Match");
                    passRF.requestFocus();
                }
                else if (pass.length() < 6) {
                    passRF.setError("Password Too short");
                    passRF.requestFocus();
                }
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(signup_form.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            student information = new student(email, pass, college, cpass, stname);
                            FirebaseDatabase.getInstance().getReference("student")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(signup_form.this, "SignUp Successful,Please Login", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), login_form.class));
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}