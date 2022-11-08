package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Herramientas extends AppCompatActivity {


    Button mostrar,pedidos,venta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramientas);
        mostrar = findViewById(R.id.btnp_a);
        pedidos = findViewById(R.id.btn_pedidos);
        venta = findViewById(R.id.btn_vv);

        venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Herramientas.this, Mostrarventa.class));
            }
        });
        pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Herramientas.this, MostrarDatos3.class));
            }
        });
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Herramientas.this, MostrarDatos.class));
            }
        });



    }




}