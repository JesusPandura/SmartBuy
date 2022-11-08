package com.example.smartbuy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarsesionActivity extends AppCompatActivity {


    public EditText correo;
    public static String cc;

    Carrito ewe = new Carrito();

    public String getCorreo () {
       return cc;
    }
    public void setCorreo(String correo) {
        this.cc = correo;
    }

    private EditText contrasena;
    private FirebaseAuth mAuth;
    public String guardado;
    Button iniciar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciarsesion);


        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contras);



        mAuth = FirebaseAuth.getInstance();

        iniciar = findViewById(R.id.iniciar_btn);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombrea = correo.getText().toString().trim();
                String precioa = contrasena.getText().toString().trim();
                if((nombrea.isEmpty() && precioa.isEmpty())||(precioa.isEmpty())||(nombrea.isEmpty()) ){
                    Toast.makeText(IniciarsesionActivity.this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
                }else{


                    iniciarsesion(nombrea, precioa);
                    ceroCont();
                }
            }
        });

        }



    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void iniciarsesion(String a, String b){

        setCorreo(a);
        mAuth.signInWithEmailAndPassword(a, b)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Conexion exitosa",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            Bundle enviaDatos = new Bundle();
                            enviaDatos.putString("keyDatos",a);
                            Intent i = new Intent(getApplicationContext(), Catalogo.class);
                            i.putExtras(enviaDatos);
                            startActivity(i);

                        } else {

                            Toast.makeText(getApplicationContext(), "Correo o contraseña no validos",Toast.LENGTH_SHORT).show();

                        }
                    }


                });
    }
    public void ceroCont() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        Double cont =  0.0;

        String zz = cont.toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cont",zz);
        editor.commit();

    }

}