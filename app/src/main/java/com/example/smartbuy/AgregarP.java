package com.example.smartbuy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AgregarP extends AppCompatActivity {


    Button btn_add;
    EditText nombre, cantidad,fechaC ,precio;
    private FirebaseFirestore mfirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_p);
        this.setTitle("Agregar Producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("id_productos");
        mfirestore = FirebaseFirestore.getInstance();

        precio = findViewById(R.id.precio_ad);
        nombre = findViewById(R.id.nombre_ad);
        cantidad = findViewById(R.id.cantidad_ad);
        fechaC  =  findViewById(R.id.fechac_ad);
        btn_add = findViewById(R.id.btn_add);

        if(id==null || id== ""){
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombrea = nombre.getText().toString().trim();
                    String precioa = precio.getText().toString().trim();
                    String cantidada = cantidad.getText().toString().trim() ;
                    String fechaCa = fechaC.getText().toString().trim();

                    if(nombrea.isEmpty() && precioa.isEmpty()&& fechaCa.isEmpty() && cantidada.isEmpty()){
                        Toast.makeText(AgregarP.this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
                    }else{

                        postpro(nombrea,precioa,cantidada,fechaCa);


                    }
                }
            });
        }else{
            btn_add.setText("Update");
            getproducto(id);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nombrea = nombre.getText().toString().trim();
                    String precioa = precio.getText().toString().trim();
                    String cantidada = cantidad.getText().toString().trim() ;
                    String fechaCa = fechaC.getText().toString().trim();
                    if(nombrea.isEmpty() && precioa.isEmpty()&& fechaCa.isEmpty() && cantidada.isEmpty()){
                        Toast.makeText(AgregarP.this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
                    }else{

                        modificarpro(nombrea,precioa,cantidada,fechaCa,id);


                    }
                }



                private void modificarpro(String nombrea, String precioa, String cantidada, String fechaCa, String id) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombrea);
                    map.put("precio", precioa);
                    map.put("cantidad", cantidada);
                    map.put("fechaC", fechaCa);
                    mfirestore.collection("productos").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AgregarP.this, "Operacion exitosa", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AgregarP.this, "Operacion erronea", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });


        }




    }
    private void getproducto(String id) {
        mfirestore.collection("productos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Toast.makeText(AgregarP.this, "Operacion lograda correctamente", Toast.LENGTH_SHORT).show();
                String namepro = documentSnapshot.getString("nombre");
                String preciopro = documentSnapshot.getString("precio");
                String cantidadpro = documentSnapshot.getString("cantidad");
                String fechaCpro = documentSnapshot.getString("fechaC");
                nombre.setText(namepro);
                precio.setText(preciopro);
                cantidad.setText(cantidadpro);
                fechaC.setText(fechaCpro);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarP.this, "Operacion erronea", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
    private static boolean isNumericc(String cadena){
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    private void postpro(String nombrea, String precioa, String cantidada, String fechaCa) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombrea);
        map.put("precio", precioa);
        map.put("cantidad", cantidada);
        map.put("fechaC", fechaCa);


        mfirestore.collection("productos").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(AgregarP.this, "Creado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarP.this, "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  false;
    }
}