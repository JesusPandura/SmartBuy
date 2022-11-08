package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Catalogo extends AppCompatActivity {

    Button mostrar,venta,historial;
    public TextView usuarioo;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        usuarioo = findViewById(R.id.usu);

        Bundle recibeDatos = getIntent().getExtras();
        String info = recibeDatos.getString("keyDatos");
        usuarioo.setText(info);

        mostrar = findViewById(R.id.btn_act);

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle enviaDDatos = new Bundle();
                enviaDDatos.putString("keyDatos",info.toString());
                Intent i = new Intent(getApplicationContext(), MostarDatos2.class);
                i.putExtras(enviaDDatos);
                startActivity(i);


            }
        });

        venta = findViewById(R.id.btnV);
        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle enviaDDatos = new Bundle();
                enviaDDatos.putString("keyDatos",info.toString());
                Intent i = new Intent(getApplicationContext(), Venta.class);
                i.putExtras(enviaDDatos);
                startActivity(i);

            }
        });

        historial = findViewById(R.id.btn_ads);
        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Historial.class);
                startActivity(i);
            }
        });


    }



    public void Herramientas(View view){
        Intent i = new Intent(this, AdminC.class);
        startActivity(i);

    }














}