package com.example.vivaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_activity);
        getSupportActionBar().setTitle("Tickets");
    }
}
