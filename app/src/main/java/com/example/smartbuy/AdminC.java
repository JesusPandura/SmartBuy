package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminC extends AppCompatActivity {
    private EditText contraseña;
    Button iniciar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_c);

        contraseña = findViewById(R.id.contrass);
        iniciar = findViewById(R.id.btn_inia);


        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contraseña.getText().toString().equals("asd")){
                    startActivity(new Intent(AdminC.this, Herramientas.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Error contraseña equivocada", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}