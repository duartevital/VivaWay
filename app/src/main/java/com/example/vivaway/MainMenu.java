package com.example.vivaway;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

import static android.nfc.NdefRecord.createMime;
import static android.nfc.NdefRecord.createTextRecord;


public class MainMenu extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uid;
    TextView username;
    TextView pass_type;
    NfcAdapter nfcAdapter;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        getSupportActionBar().setTitle("VivaWay");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.viva_green)));

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        username = findViewById(R.id.user_name_tv);
        pass_type = findViewById(R.id.pass_type_tv);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter==null)
            Log.e("NFC", "NFC is not supported");
        uid = firebaseAuth.getCurrentUser().getUid();
        generateQRcode(uid);
        docRef = firestore.collection("users").document(uid);

        nfcAdapter.setNdefPushMessageCallback(this, this);
    }

    @Override
    public void onStart(){
        getProfileDetails();
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        nfcAdapter.setNdefPushMessageCallback(this, this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // / Make sure the latest Intent will be used in OnResume() that follows
        setIntent(intent);
    }

    public void doBuy(View view){
        Intent intent = new Intent(this, BuyActivity.class);
        startActivity(intent);
    }

    public void doProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void generateQRcode(String content) {
        ImageView qrCode = findViewById(R.id.qr_code_img);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void getProfileDetails(){
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot != null){
                    username.setText(documentSnapshot.getString("Name"));
                    pass_type.setText(documentSnapshot.getString("Pass"));
                }else{
                    Log.d("LOGGER", "No such document");
                }
            }
        });
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String text = uid;
        NdefMessage ndefMessage = new NdefMessage(
                new NdefRecord[] { createTextRecord(
                        null, text)
                });
        return ndefMessage;
    }
}
