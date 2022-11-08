package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Catalogo extends AppCompatActivity {

    Button mostrar,venta,historial,cerrar;
    public TextView usuarioo;
    public static String coco;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        usuarioo = findViewById(R.id.usu);

        Bundle recibeDatos = getIntent().getExtras();
        String info = recibeDatos.getString("keyDatos");
        usuarioo.setText(info);
        coco = info;

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
                Bundle enviaDDatos = new Bundle();
                enviaDDatos.putString("keyDatos",info.toString());
                Intent i = new Intent(getApplicationContext(), Historial.class);
                i.putExtras(enviaDDatos);
                startActivity(i);
            }
        });

        cerrar = findViewById(R.id.btn_ads4);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Catalogo.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(Catalogo.this, "Exito", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void Herramientas(View view){
        Intent i = new Intent(this, AdminC.class);
        startActivity(i);

    }














}