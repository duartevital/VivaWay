package com.example.vivaway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BuyActivity extends AppCompatActivity {

    private boolean inLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_activity);
        getSupportActionBar().setTitle("Tickets");
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

