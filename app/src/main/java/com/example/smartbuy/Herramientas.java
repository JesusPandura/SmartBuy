package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Herramientas extends AppCompatActivity {


    Button mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramientas);




    }

    public void agregar(View view){
        Intent i = new Intent(this, AgregarP.class);
        startActivity(i);

    }


}