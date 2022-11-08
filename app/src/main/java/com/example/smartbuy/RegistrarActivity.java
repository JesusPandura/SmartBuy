package com.example.smartbuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private EditText correo;
    private EditText contrasena;
    private EditText contrasenaConfirmacion;
    CheckBox admin, cliente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);



        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
       // updateUI(currentUser);

        mAuth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contras);
        contrasenaConfirmacion = findViewById(R.id.Confirmacion_contrase);
        admin = findViewById(R.id.btn_ad);
        cliente = findViewById(R.id.btn_cliente);

    }

    public void registrarusuario(View view) {

        if(contrasenaConfirmacion.getText().toString().equals(contrasena.getText().toString())){

            mAuth.createUserWithEmailAndPassword(correo.getText().toString(), contrasena.getText().toString())
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>(){
                        @Override
                        public void onComplete( Task<AuthResult> task) {

                            if (task.isSuccessful()) {


                                Toast.makeText(getApplicationContext(), "Usuario creado",Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                validar ();

                            } else {

                                Toast.makeText(getApplicationContext(), "Correo o contraseña no validos",Toast.LENGTH_SHORT).show();

                            }
                        }


                    });

            }else {

                Toast.makeText(this, "Las contraseña no es valida/o no coinciden",Toast.LENGTH_SHORT).show();

             }


        }
        public void validar (){
        String cad = "Selecione su tipo de usuario";

            if(admin.isChecked()==true){
                Toast.makeText(getApplicationContext(), "Administrador",Toast.LENGTH_SHORT).show();
            }
            if(cliente.isChecked()==true){
                Toast.makeText(getApplicationContext(), "Cliente",Toast.LENGTH_SHORT).show();
            }

         }


    }