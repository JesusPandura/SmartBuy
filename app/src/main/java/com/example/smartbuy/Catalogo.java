package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Catalogo extends AppCompatActivity {

    Button mostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);
        mostrar = findViewById(R.id.btn_act);

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Catalogo.this, MostrarDatos.class));
            }
        });
    }



    public void Herramientas(View view){
        Intent i = new Intent(this, Herramientas.class);
        startActivity(i);

    }














}