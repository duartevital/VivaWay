package com.example.vivaway;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    DocumentReference docRef;
    TextView name_tv;
    TextView pass_type_tv;
    TextView zapping_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.viva_green)));

        name_tv = findViewById(R.id.name_tv);
        pass_type_tv = findViewById(R.id.pass_type_tv);
        zapping_value = findViewById(R.id.zapping_value);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        docRef = firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
    }

    @Override
    public void onStart(){
        getProfileDetails();
        super.onStart();
    }

    private void getProfileDetails(){
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot != null){
                    name_tv.setText(documentSnapshot.getString("Name"));
                    pass_type_tv.setText(documentSnapshot.getString("Pass"));
                }else{
                    Log.d("LOGGER", "No such document");
                }
            }
        });
    }

}
