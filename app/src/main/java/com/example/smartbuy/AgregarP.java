package com.example.smartbuy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AgregarP extends AppCompatActivity {


    Button btn_add,ff;
    EditText nombre, cantidad,fechaC ,precio;
    private FirebaseFirestore mfirestore;
    private int dia;
    private int mes;
    private int año;
    private int hora;
    private int minuto;
    public final int NOTIFICACION_ID = 0;
    public final String  CHANNEL_ID = "NOTIFICACION";
    private PendingIntent pendingIntent;

    private static final String Date_REGEX =
            "^(?:(?:(?:0?[13578]|1[02])(\\/|-|\\.)31)\\1|" +
                    "(?:(?:0?[1,3-9]|1[0-2])(\\/|-|\\.)(?:29|30)\\2))" +
                    "(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:0?2(\\/|-|\\.)29\\3" +
                    "(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|" +
                    "[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|" +
                    "^(?:(?:0?[1-9])|(?:1[0-2]))(\\/|-|\\.)(?:0?[1-9]|1\\d|" +
                    "2[0-8])\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

    private static final Pattern Date_PATTERN = Pattern.compile(Date_REGEX);

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

                    if((nombrea.isEmpty() && precioa.isEmpty()&& fechaCa.isEmpty() && cantidada.isEmpty() ) || precioa.isEmpty() || fechaCa.isEmpty() || precioa.isEmpty() ||  nombrea.isEmpty()){
                        Toast.makeText(AgregarP.this, "Campos obligatorios", Toast.LENGTH_SHORT).show();
                    }else{


                        if(isNumericc(precioa)==true && isNumericc(cantidada)==true ){

                            if(dateValidator(fechaCa) == true){

                                if((validateminusculas(nombrea)==true && validatemayusculas(nombrea)==true) || validateminusculas(nombrea)==true || validatemayusculas(nombrea)==true ){

                                    postpro( nombrea,  precioa, cantidada,  fechaCa);
                                    createNotificationChannel();
                                    createNotificationAP();

                                }else{


                                    Toast.makeText(AgregarP.this, "Escriba bien el nombre de producto", Toast.LENGTH_SHORT).show();
                                }



                            }else{
                                Toast.makeText(AgregarP.this, "Formato invalido de fecha", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(AgregarP.this, "Coloque un numero", Toast.LENGTH_SHORT).show();

                        }




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


                        if(isNumericc(precioa)==true && isNumericc(cantidada)==true ){


                            if(dateValidator(fechaCa) == true){

                                if((validateminusculas(nombrea)==true && validatemayusculas(nombrea)==true) || validateminusculas(nombrea)==true || validatemayusculas(nombrea)==true ){

                                    modificarpro(nombrea,precioa,cantidada,fechaCa,id);
                                    createNotificationChannel();
                                    createNotificationMP();
                                }else{


                                    Toast.makeText(AgregarP.this, "Escriba bien el nombre de producto", Toast.LENGTH_SHORT).show();
                                }



                            }else{
                                Toast.makeText(AgregarP.this, "Formato invalido de fecha", Toast.LENGTH_SHORT).show();
                            }







                        }else{
                            Toast.makeText(AgregarP.this, "Coloque un numero", Toast.LENGTH_SHORT).show();

                        }




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
    private void muestrafecha() {
        Calendar f = Calendar.getInstance();
        dia =f.get(Calendar.DAY_OF_MONTH);
        mes = f.get(Calendar.MONTH);
        año = f.get(Calendar.YEAR);
        hora = f.get(Calendar.HOUR);
        minuto = f.get(Calendar.MINUTE);
        if (minuto  < 10 ){

            fechaC.setText(dia+"/"+mes+"/"+año+"/"+hora+":"+"0"+minuto);

        }else{
            fechaC.setText(dia+"/"+mes+"/"+año+"/"+hora+":"+minuto);

        }

    }
    public static boolean dateValidator(String date)
    {
        Matcher matcher = Date_PATTERN.matcher(date);
        return matcher.matches();
    }

    private static  boolean isFecha(String cadena){
        try {

           dateValidator(cadena);
            return true;

        } catch (NumberFormatException nfe){
            return false;
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

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createNotificationAP() {
        setPeding(MostarDatos2.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_beach_access_24)
                .setContentTitle("Se agrego un producto nuevo")
                .setContentText(nombre.getText().toString()+"\n"+"a solo"+"\n"+precio.getText().toString()+"$")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    private void setPeding(Class <?> MostarDatos2){
        Catalogo  l = new Catalogo();
        String correo = l.coco;
        Bundle enviaDDatos = new Bundle();
        enviaDDatos.putString("keyDatos", correo );
        Intent intent = new Intent(this, MostarDatos2);
        intent.putExtras(enviaDDatos);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MostarDatos2);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    private void createNotificationMP() {
        setPeding(MostarDatos2.class);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_beach_access_24)
                .setContentTitle("Se agrego un stock nuevo"+"\n"+nombre.getText().toString())
                .setContentText("la cantidad es"+"\n"+cantidad.getText().toString()+"\n"+"a"+"\n"+precio.getText().toString()+"$")
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  false;
    }
}