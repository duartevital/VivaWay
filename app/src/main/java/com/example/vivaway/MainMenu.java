package com.example.vivaway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        getSupportActionBar().setTitle("VivaWay");
    }

    public void doBuy(View view){
        Intent intent = new Intent(this, BuyActivity.class);
        startActivity(intent);
    }

    public void doProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
