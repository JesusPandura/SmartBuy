package com.example.smartbuy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Venta extends AppCompatActivity {

    private int dia;
    private int mes;
    private int año;
    private int hora;
    private int minuto;
    Button fecha,ventaa,confianzaa,cancel;
    TextView correo,total,fechaa;
    com.example.smartbuy.Catalogo f = new Catalogo();
    private FirebaseAuth mAuth;
    private EditText contraseña;
    private FirebaseFirestore mfirestore;
    static int idpedido = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        correo = findViewById(R.id.txtcorreo);

        Bundle recibeDDatos = getIntent().getExtras();
        String info = recibeDDatos.getString("keyDatos");
        correo.setText(info);

        total = findViewById(R.id.txtCosto);

        getCont();

        fechaa = findViewById(R.id.fecha_v);
        fecha =  findViewById(R.id.btn_f);

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muestrafecha();
            }
        });
        contraseña = findViewById(R.id.contraseña_v);
        ventaa = findViewById(R.id.btn_venta);
        mAuth = FirebaseAuth.getInstance();
        mfirestore = FirebaseFirestore.getInstance();
        confianzaa = findViewById(R.id.btn_con);
        cancel = findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ceroCont();
            }
        });

        ventaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                String conti =  preferences.getString("cont","holis");

                String correoa = correo.getText().toString().trim();
                String total =  conti.trim();
                String fechaCa = fechaa.getText().toString().trim();
                String contra = contraseña.getText().toString().trim();


                if((fechaCa.isEmpty()  &&  contra.isEmpty()) || (fechaCa.isEmpty()) || (contra.isEmpty())){

                    Toast.makeText(Venta.this, "Campos obligatorios", Toast.LENGTH_SHORT).show();

                }else{


                    mAuth.signInWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString())
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete( Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(Venta.this, "Conexion exitosa",Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        int padrino = 1;
                                        idpedido = idpedido + padrino;
                                        ventaa(idpedido,correoa,total,fechaCa);
                                        ceroCont();
                                        Intent i = new Intent(getApplicationContext(), paypal.class);
                                        startActivity(i);

                                    } else {

                                        Toast.makeText(getApplicationContext(), "Correo o contraseña no validos",Toast.LENGTH_SHORT).show();

                                    }
                                }


                            });


                }

            }
        });
        confianzaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                String conti =  preferences.getString("cont","holis");

                String correoa = correo.getText().toString().trim();
                String total =  conti.trim();
                String fechaCa = fechaa.getText().toString().trim();
                String contra = contraseña.getText().toString().trim();



                if((fechaCa.isEmpty()  &&  contra.isEmpty()) || (fechaCa.isEmpty()) || (contra.isEmpty())){

                    Toast.makeText(Venta.this, "Campos obligatorios", Toast.LENGTH_SHORT).show();

                }else{


                    mAuth.signInWithEmailAndPassword(correo.getText().toString(), contraseña.getText().toString())
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete( Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(Venta.this, "Conexion exitosa",Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        int padrino = 1;
                                        idpedido = idpedido + padrino;
                                        ventaaa(idpedido,  correoa,  fechaCa, total);
                                        ceroCont();
                                        Intent i = new Intent(getApplicationContext(), paypal.class);
                                        startActivity(i);
                                    } else {

                                        Toast.makeText(getApplicationContext(), "Correo o contraseña no validos",Toast.LENGTH_SHORT).show();

                                    }
                                }


                            });


                }
            }
        });
    }

    public void getCont() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String conti =  preferences.getString("cont","holis");
        total.setText("Total a pagar = " + conti.toString());

    }
    private void muestrafecha() {
        Calendar f = Calendar.getInstance();
        dia =f.get(Calendar.DAY_OF_MONTH);
        mes = f.get(Calendar.MONTH);
        año = f.get(Calendar.YEAR);
        hora = f.get(Calendar.HOUR);
        minuto = f.get(Calendar.MINUTE);
        if (minuto  < 10 ){

            fechaa.setText(dia+"/"+mes+"/"+año+"/"+hora+":"+"0"+minuto);

        }else{
            fechaa.setText(dia+"/"+mes+"/"+año+"/"+hora+":"+minuto);

        }

    }


    private void ventaa(Integer idpedido, String correoa, String fechaCa, String total) {
        String estado = "N/P";
        String confianza = "No";
        Map<String, Object> mapp = new HashMap<>();
        mapp.put("idpedido", idpedido);
        mapp.put("correo", correoa);
        mapp.put("total",fechaCa);
        mapp.put("fecha", total);
        mapp.put("estado", estado);
        mapp.put("C_confianza", confianza);

        mfirestore.collection("pedidos").add(mapp).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(Venta.this, "Creado correctamente", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Venta.this, "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void ventaaa(Integer idpedido, String correoa, String fechaCa, String total) {
        String estado = "N/P";
        String confianza = "Si";
        Map<String, Object> mapp = new HashMap<>();
        mapp.put("idpedido", idpedido);
        mapp.put("correo", correoa);
        mapp.put("total",fechaCa);
        mapp.put("fecha", total);
        mapp.put("estado", estado);
        mapp.put("C_confianza", confianza);

        mfirestore.collection("pedidos").add(mapp).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(Venta.this, "Creado correctamente", Toast.LENGTH_SHORT).show();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Venta.this, "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void ceroCont() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        Double cont =  0.0;
        String zz = cont.toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cont",zz);
        total.setText("Total a pagar = " + zz.toString());
        editor.commit();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  false;
    }
}

