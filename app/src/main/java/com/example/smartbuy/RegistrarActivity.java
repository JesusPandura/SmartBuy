package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button btn_registrar;
    private EditText correo;
    private EditText contrasena;
    private EditText contrasenaConfirmacion;
    private EditText nombre;
    CheckBox admin, cliente;
    private FirebaseFirestore mfirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("id_productos");
        mfirestore = FirebaseFirestore.getInstance();

        correo = findViewById(R.id.correo);
        admin = findViewById(R.id.btn_ad);
        cliente = findViewById(R.id.btn_cliente);
        nombre = findViewById(R.id.nombre_r);
        btn_registrar = findViewById(R.id.button);

        if(id==null || id== ""){
            btn_registrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombrea = nombre.getText().toString().trim();
                    String correoa = correo.getText().toString().trim();

                    if(nombrea.isEmpty() && correoa.isEmpty()){
                        Toast.makeText(RegistrarActivity.this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
                    }else{

                        postre(nombrea,correoa);
                        registrarusuario( view);
                    }
                }
            });
        }
        mAuth = FirebaseAuth.getInstance();



    }

    private void postre(String nombrea, String correoa) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombrea);
        map.put("correo", correoa);


        mfirestore.collection("usuario").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(RegistrarActivity.this, "Creado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrarActivity.this, "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });


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
        nombre = findViewById(R.id.nombre_r);



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