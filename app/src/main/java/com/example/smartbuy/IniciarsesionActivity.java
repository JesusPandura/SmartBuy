package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarsesionActivity extends AppCompatActivity {


    private EditText correo;
    private EditText contrasena;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciarsesion);


        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contras);


        mAuth = FirebaseAuth.getInstance();

    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    public void iniciarsesion(View view){

        mAuth.signInWithEmailAndPassword(correo.getText().toString(), contrasena.getText().toString())
                .addOnCompleteListener( new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Conexion exitosa",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent i = new Intent(getApplicationContext(), Catalogo.class);
                            startActivity(i);

                        } else {

                            Toast.makeText(getApplicationContext(), "Correo o contraseña no validos",Toast.LENGTH_SHORT).show();

                        }
                    }


                });
    }
}