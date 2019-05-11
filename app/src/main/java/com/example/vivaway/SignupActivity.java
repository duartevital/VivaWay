package com.example.vivaway;

import android.content.Intent;
import android.renderscript.ScriptGroup;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {

    EditText email_et;
    EditText username_et;
    EditText password_et;
    Button signup_btn;
    TextView login_tv;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        email_et = findViewById(R.id.email_et);
        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
        signup_btn = findViewById(R.id.signup_btn);
        login_tv = findViewById(R.id.login_tv);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void doSignUp(View view){
        final Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        if(!checkExceptions(view)) {
            firebaseAuth.createUserWithEmailAndPassword(email_et.getText().toString(), username_et.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(intent);
                    } else
                        Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void goLogin(View view){
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
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
