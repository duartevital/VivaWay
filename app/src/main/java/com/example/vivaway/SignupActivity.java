package com.example.vivaway;

import android.content.Intent;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.UserProfileChangeRequest.Builder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1beta1.WriteResult;

import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    public static final String EMAIL_KEY = "Email";
    public static final String NAME_KEY = "Name";
    public static final String PASS_KEY = "Pass";

    EditText email_et;
    EditText username_et;
    EditText password_et;
    Button signup_btn;
    TextView login_tv;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    private CollectionReference colRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        getSupportActionBar().hide();

        email_et = findViewById(R.id.email_et);
        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
        signup_btn = findViewById(R.id.signup_btn);
        login_tv = findViewById(R.id.login_tv);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void doSignUp(View view){
        final Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        if(!checkExceptions(view)) {
            firebaseAuth.createUserWithEmailAndPassword(email_et.getText().toString(), username_et.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if(user!=null){
                            insertUser(user.getUid());
                            startActivity(intent);
                        }
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

    public void insertUser(String user_id){
        colRef = FirebaseFirestore.getInstance().collection("users");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(EMAIL_KEY, email_et.getText().toString());
        data.put(NAME_KEY, username_et.getText().toString());
        data.put(PASS_KEY, "null");
        colRef.document(user_id).set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DOC", "Document created successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DOC", "Error while creating document");
                    }
                });
    }
}
