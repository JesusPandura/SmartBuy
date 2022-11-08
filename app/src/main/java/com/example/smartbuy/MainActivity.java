package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void iniciar(View view){
        Intent i = new Intent(this, IniciarsesionActivity.class);
        startActivity(i);

    }
    public void registrar(View view){
        Intent i = new Intent(this, RegistrarActivity.class);
        startActivity(i);
    }











}