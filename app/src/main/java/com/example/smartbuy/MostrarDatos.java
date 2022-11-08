package com.example.smartbuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbuy.adapter.productoadpater;
import com.example.smartbuy.modelo.producto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MostrarDatos extends AppCompatActivity {

    RecyclerView recy;
    productoadpater mAdapter;
    FirebaseFirestore mfirestore;
    ImageButton btn_mas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);
        recy =  findViewById(R.id.recyy);

        recy.setLayoutManager(new LinearLayoutManager(this));

        mfirestore = FirebaseFirestore.getInstance();
        Query query = mfirestore.collection("productos");

        FirestoreRecyclerOptions<producto> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<producto>().setQuery(query,producto.class).build();

        mAdapter = new  productoadpater(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        recy.setAdapter(mAdapter);

        btn_mas = findViewById(R.id.btn_mas4);
        btn_mas.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View view) { startActivity(new Intent(MostrarDatos.this, AgregarP.class)); } });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}