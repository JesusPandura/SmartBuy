package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class paypal extends AppCompatActivity {
    Button render, back;
    TextView cc;

    Venta nn = new Venta();
    String info = nn.velo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        render = findViewById(R.id.btn_h);
        back =  findViewById(R.id.btn_h2);
        cc =  findViewById(R.id.correo12);

        cc.setText(info);


    }

    public void catalogo(View view){


        Bundle enviaDDatos = new Bundle();
        enviaDDatos.putString("keyDatos",info);
        Intent i = new Intent(getApplicationContext(), Catalogo.class);
        i.putExtras(enviaDDatos);
        startActivity(i);

    }
    public void historial(View view){


        Bundle enviaDDatos = new Bundle();
        enviaDDatos.putString("keyDatos",info);
        Intent i = new Intent(getApplicationContext(),Historial.class);
        i.putExtras(enviaDDatos);
        startActivity(i);

    }




}