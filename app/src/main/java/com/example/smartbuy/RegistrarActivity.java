package com.example.smartbuy;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.regex.Pattern;

public class RegistrarActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button btn_registrar;
    private EditText correo;
    private EditText contrasena;
    private EditText contrasenaConfirmacion;
    private EditText nombre;
    CheckBox admin, cliente;
    private FirebaseFirestore mfirestore;
    TextView atoz, AtoZ, num, charcount, colorsit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("id_productos");
        mfirestore = FirebaseFirestore.getInstance();

        correo = findViewById(R.id.correo);
        admin = findViewById(R.id.btn_ad);

        nombre = findViewById(R.id.nombre_r);
        btn_registrar = findViewById(R.id.button);


        atoz = findViewById(R.id.qw);
        AtoZ = findViewById(R.id.qwe);
        num = findViewById(R.id.qwer);
        charcount = findViewById(R.id.qwert);
        colorsit = findViewById(R.id.colorsito);
        colorsit.setTextColor(Color.GREEN);



        if(id==null || id== ""){
            btn_registrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombrea = nombre.getText().toString().trim();
                    String correoa = correo.getText().toString().trim();

                    if(nombrea.isEmpty() && correoa.isEmpty()){
                        Toast.makeText(RegistrarActivity.this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
                    }else{
                        if((validateminusculas(nombrea)==true && validatemayusculas(nombrea)==true) || validateminusculas(nombrea)==true || validatemayusculas(nombrea)==true ){

                            if(charcount.getTextColors() == colorsit.getTextColors() && AtoZ.getTextColors() == colorsit.getTextColors() && atoz.getTextColors() == colorsit.getTextColors() && num.getTextColors() == colorsit.getTextColors()){
                                postre(nombrea,correoa);
                                registrarusuario(view);
                            }else{
                                Toast.makeText(RegistrarActivity.this, "Mal formato", Toast.LENGTH_SHORT).show();
                            }

                        }else{


                            Toast.makeText(RegistrarActivity.this, "Escriba bien el nombre", Toast.LENGTH_SHORT).show();
                        }




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
                Toast.makeText(RegistrarActivity.this, "Smart buy", Toast.LENGTH_SHORT).show();

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

        nombre = findViewById(R.id.nombre_r);

        contrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = contrasena.getText().toString();
                validatepass(password);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


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

                                Toast.makeText(RegistrarActivity.this, "Correo o contraseña no validos",Toast.LENGTH_SHORT).show();

                            }
                        }


                    });

            }else {

                Toast.makeText(RegistrarActivity.this, "Las contraseña no es valida/o no coinciden",Toast.LENGTH_SHORT).show();

             }


        }
    public void validatepass(String password) {

        // check for pattern
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            atoz.setTextColor(Color.RED);
        } else {
            // if lowercase character is  present
            atoz.setTextColor(Color.GREEN);
        }

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            AtoZ.setTextColor(Color.RED);
        } else {
            // if uppercase character is  present
            AtoZ.setTextColor(Color.GREEN);
        }
        // if digit is not present
        if (!digit.matcher(password).find()) {
            num.setTextColor(Color.RED);
        } else {
            // if digit is present
            num.setTextColor(Color.GREEN);
        }
        // if password length is less than 8
        if (password.length() < 8) {
            charcount.setTextColor(Color.RED);
        } else {
            charcount.setTextColor(Color.GREEN);
        }


    }
    public static boolean validateminusculas(String password) {
        Pattern lowercase = Pattern.compile("[a-z]");

        if (!lowercase.matcher(password).find()) {
            return false;
        } else {
            // if lowercase character is  present
            return true;

        }


    }

    public static boolean validatemayusculas(String password) {
        Pattern lowercase = Pattern.compile("[A-Z]");

        if (!lowercase.matcher(password).find()) {
            return false;
        } else {
            // if lowercase character is  present
            return true;

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