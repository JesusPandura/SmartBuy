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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Modipedido extends AppCompatActivity {

        public final int NOTIFICACION_ID = 0;
             public final String  CHANNEL_ID = "NOTIFICACION";
             private PendingIntent pendingIntent;
        EditText uno,dos,tres,cuatro,cinco,seis;
        Button modi,modi1,modi2;
         Venta nn = new Venta();
         String info = nn.velo;
        private FirebaseFirestore mfirestore;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_modipedido);
            this.setTitle("Editar Pedido");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            String id = getIntent().getStringExtra("id_pedidos");


            uno = findViewById(R.id.txt1);
            dos = findViewById(R.id.txt2);
            tres = findViewById(R.id.txt3);
            cuatro = findViewById(R.id.txt4);
            cinco= findViewById(R.id.txt5);
            seis = findViewById(R.id.txt6);
            modi = findViewById(R.id.modificarP);
            modi1 = findViewById(R.id.modificarP2);
            modi2 = findViewById(R.id.modificarP4);
            mfirestore = FirebaseFirestore.getInstance();
            getproducto(id);



            modi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String unoo = uno.getText().toString().trim();
                    String doss = dos.getText().toString().trim();
                    String tress = tres.getText().toString().trim();
                    String cuatr = cuatro.getText().toString().trim();
                    String cinc = cinco.getText().toString().trim();
                    String seiss = seis.getText().toString().trim();



                    cinc = "S/P";
                    modificar(unoo, doss, tress, cuatr, cinc , seiss, id);
                    createNotificationChannel();
                    createNotification();
                }
            });

            modi1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String unoo = uno.getText().toString().trim();
                    String doss = dos.getText().toString().trim();
                    String tress = tres.getText().toString().trim();
                    String cuatr = cuatro.getText().toString().trim();
                    String cinc = cinco.getText().toString().trim();
                    String seiss = seis.getText().toString().trim();



                    cinc = "Listo";
                    modificar(unoo, doss, tress, cuatr, cinc , seiss, id);
                    createNotificationChannel();
                    createNotification();
                }
            });

            modi2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String unoo = uno.getText().toString().trim();
                    String doss = dos.getText().toString().trim();
                    String tress = tres.getText().toString().trim();
                    String cuatr = cuatro.getText().toString().trim();
                    String cinc = cinco.getText().toString().trim();
                    String seiss = seis.getText().toString().trim();



                    cinc = "N/P";
                    modificar(unoo, doss, tress, cuatr, cinc , seiss, id);
                    createNotificationChannel();
                    createNotification();
                }
            });




        }


        private void getproducto(String id) {
            mfirestore.collection("pedidos").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Toast.makeText(Modipedido.this, "Operacion lograda correctamente", Toast.LENGTH_SHORT).show();
                    String iddd  = documentSnapshot.getDouble("idpedido").toString();
                    String namepro = documentSnapshot.getString("correo");
                    String preciopro = documentSnapshot.getString("fecha");
                    String cantidadpro = documentSnapshot.getString("total");
                    String fechaCpro = documentSnapshot.getString("estado");
                    String cli = documentSnapshot.getString("C_confianza");

                    uno.setText(iddd);
                    dos.setText(namepro);
                    tres.setText(preciopro);
                    cuatro.setText(cantidadpro);
                    cinco.setText(fechaCpro);
                    seis.setText(cli);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Modipedido.this, "Operacion erronea", Toast.LENGTH_SHORT).show();
                }
            });
        }
        private void modificar(String unoo, String doss, String tress, String cuatr, String cinc, String seiss, String id) {

            Map<String, Object> map = new HashMap<>();
            map.put("correo", doss);
            map.put("fecha", tress);
            map.put("total", cuatr);
            map.put("estado", cinc);
            map.put("C_confianza", seiss);
            mfirestore.collection("pedidos").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(Modipedido.this, "Operacion exitosa", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Modipedido.this, "Operacion erronea", Toast.LENGTH_SHORT).show();
                }
            });

        }

        private void createNotificationChannel(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                CharSequence name = "Noticacion";
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        private void createNotification() {
            setPeding(Historial.class);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_beach_access_24)
                    .setContentTitle("Estado del pedido")
                    .setContentText("Tu pedido se ha actualizado")
                    .setColor(Color.BLUE)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
        }

    private void setPeding(Class <?> Historial){

        Bundle enviaDDatos = new Bundle();
        enviaDDatos.putString("keyDatos", dos.getText().toString());
        Intent intent = new Intent(this, Historial);
        intent.putExtras(enviaDDatos);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(Historial);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}