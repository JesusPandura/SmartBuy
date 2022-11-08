package com.example.smartbuy;

import android.content.Context;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Carrito extends AppCompatActivity {

    Double cont = 0.0;

    Button carrito,cancelar,fecha, vaciar;
    EditText  cantidad , precio, producto;
    private FirebaseFirestore mfirestore;
    private TextView usuario,fechaa,total;
    static double cantii = 0.0;

    private int dia;
    private int mes;
    private int año;
    private int hora;
    private int minuto;
    private static String Guard;

    static MostarDatos2 u = new MostarDatos2();
    static final String inf = u.cc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        this.setTitle("Agregar Producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("id_venta");
        mfirestore = FirebaseFirestore.getInstance();

        precio = findViewById(R.id.preci);
        cantidad = findViewById(R.id.cant);
        producto  =  findViewById(R.id.namepro);
        carrito= findViewById(R.id.btn_car);
        usuario = findViewById(R.id.usae);
        usuario.setText(inf);
        fechaa = findViewById(R.id.fecha_t);
        fecha = findViewById(R.id.btn_fecha);
        total = findViewById(R.id.total);
        vaciar = findViewById(R.id.btn_vaciar);
        vaciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ceroCont();
            }
        });
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muestrafecha();




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
        });


        getCont();

        if(id==null || id== "") {




        }else{
            getproducto(id);

            carrito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Pattern digit = Pattern.compile("[0-9]");
                    Pattern uppercase = Pattern.compile("[A-Z]");
                    Pattern lowercase = Pattern.compile("[a-z]");
                    String nombrea = producto.getText().toString().trim();
                    String precioa = precio.getText().toString().trim();
                    String cantidada = cantidad.getText().toString().trim() ;
                    String usuarioo = usuario.getText().toString();
                    String fechaaa = fechaa.getText().toString();

                    if(isNumericc(precioa)==true && isNumericc(cantidada)==true ){
                        if(( cantidada.isEmpty())  || (Double.parseDouble(cantidada) > cantii) || (fechaaa.isEmpty()) || (cantidada.isEmpty() && fechaaa.isEmpty() )){
                            Toast.makeText(Carrito.this, "Campos obligatorios/stock superado/falta generar fecha", Toast.LENGTH_SHORT).show();
                        }else{
                            if(Double.parseDouble(cantidada) == 0.0 ) {
                                Toast.makeText(Carrito.this, "No hay Stock", Toast.LENGTH_SHORT).show();
                            }else{
                                cantidad( cantidada,  id, Guard);

                                Carritopro(nombrea,precioa,cantidada,usuarioo,fechaaa);

                                Double lola =  (Double.parseDouble(precio.getText().toString()))*(Double.parseDouble(cantidad.getText().toString()));
                                setCont(lola);
                            }


                        }

                    }else{
                        Toast.makeText(Carrito.this, "Coloque un numero", Toast.LENGTH_SHORT).show();

                    }



                }



                private void Carritopro(String nombrea, String precioa, String cantidada, String usuarioo, String fechaaa) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("producto", nombrea);
                    map.put("precio", precioa);
                    map.put("cantidad", cantidada);
                    map.put("usuario", usuarioo);
                    map.put("fecha", fechaaa);

                    mfirestore.collection("venta").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(Carrito.this, "Creado correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Carrito.this, "Error al ingresar", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                private void cantidad( String cantidada, String id, String Guard ){


                      Double  cantidadN =   Double.parseDouble(Guard) - Double.parseDouble(cantidada);

                      String canti= cantidadN.toString();

                    Map<String, Object> map = new HashMap<>();
                    map.put("cantidad", canti);
                    mfirestore.collection("productos").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Carrito.this, "Operacion exitosa", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Carrito.this, "Operacion erronea", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Carrito.this, "Operacion lograda correctamente", Toast.LENGTH_SHORT).show();
                String nameproo = documentSnapshot.getString("nombre");
                String preciopro = documentSnapshot.getString("precio");
                String cantidadpro = documentSnapshot.getString("cantidad");
                producto.setText(nameproo);
                precio.setText(preciopro);
                cantidad.setText(cantidadpro);
                cantii = Double.parseDouble(cantidadpro);
                Guard = cantidadpro;


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Carrito.this, "Operacion erronea", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void setCont(Double g) {
      SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        cont =   g + Double.parseDouble(total.getText().toString());
        String zz = cont.toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cont",zz);
        total.setText(zz.toString());
        editor.commit();

    }

    public void ceroCont() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        cont =  0.0;
        String zz = cont.toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cont",zz);
        total.setText(zz.toString());
        editor.commit();

    }

    public void getCont() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String conti =  preferences.getString("cont","holis");
        total.setText(conti.toString());

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  false;
    }

    private static  boolean isNumericc(String cadena){
        try {
            Double.parseDouble(cadena);
            return true;

        } catch (NumberFormatException nfe){
            return false;
        }
    }

    private static boolean isString(String cadena){
        try {
            String.valueOf(cadena);
            return true;

        } catch (NumberFormatException nfe){
            return false;
        }
    }



}