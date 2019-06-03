package com.example.vivaway;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BuyActivity extends AppCompatActivity {

    static final String PASS_KEY = "Pass";
    static final String VALID_KEY = "Valid";
    private boolean inLayout = false;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private String uid;
    private CollectionReference colRef;

    private String pass_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_activity);
        getSupportActionBar().setTitle("Passes");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.viva_green)));

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        uid = user != null ? user.getUid() : null;
        colRef = firestore.collection("users");
        pass_str = "null";
    }

    public void changeLayout(View view){
        switch(view.getId()){
            case R.id.passe_proprio_btn:
                setContentView(R.layout.passe_proprio_layout);
                getSupportActionBar().setTitle("Passes Próprios");
                inLayout = true;
                break;
            case R.id.passe_navegante_btn:
                setContentView(R.layout.passe_navegante_layout);
                getSupportActionBar().setTitle("Passes Navegante");
                inLayout = true;
                break;
            case R.id.passe_combinado_btn:
                setContentView(R.layout.passe_combinado_layout);
                getSupportActionBar().setTitle("Passes Combinados");
                inLayout = true;
                break;
        }
    }

    public void getButtonPressed(View view){
        Button button = (Button) view;
        pass_str = button.getText().toString();
        insertPass();
    }

    public void insertPass(){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(PASS_KEY, pass_str);
        data.put(VALID_KEY, true);
        colRef.document(uid).update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(BuyActivity.this, "Passe adicionado: "+pass_str, Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BuyActivity.this, "Não foi possível adicionar passe!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onBackPressed(){
        if(inLayout){
            setContentView(R.layout.buy_activity);
            inLayout = false;
        }else{
            super.onBackPressed();
        }
    }
}

