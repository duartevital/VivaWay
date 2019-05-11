package com.example.vivaway;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email_et;
    EditText password_et;
    Button login_btn;
    TextView signup_tv;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getSupportActionBar().hide();

        email_et = findViewById(R.id.email_et);
        password_et = findViewById(R.id.password_et);
        login_btn = findViewById(R.id.login_btn);
        signup_tv = findViewById(R.id.signup_tv);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void doLogin(View view){
        final Intent intent = new Intent(LoginActivity.this, MainMenu.class);
        if (!checkExceptions(view)) {
            firebaseAuth.signInWithEmailAndPassword(email_et.getText().toString(), password_et.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                        startActivity(intent);
                    else
                        Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void goRegister(View view){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    public boolean checkExceptions(View view) {
        boolean exception = false;
        if (TextUtils.isEmpty(email_et.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_LONG).show();
            exception = true;
        }
        if (TextUtils.isEmpty(password_et.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_LONG).show();
            exception = true;
        }
        return exception;
    }
}
