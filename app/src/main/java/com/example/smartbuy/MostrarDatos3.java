package com.example.smartbuy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbuy.adapter.productoadpater3;
import com.example.smartbuy.modelo.pedido;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MostrarDatos3 extends AppCompatActivity {

    RecyclerView recy;
    productoadpater3 mAdapter;
    FirebaseFirestore mfirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos3);
        recy =  findViewById(R.id.re);

        recy.setLayoutManager(new LinearLayoutManager(this));

        mfirestore = FirebaseFirestore.getInstance();
        Query query = mfirestore.collection("pedidos");

        FirestoreRecyclerOptions<pedido> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<pedido>().setQuery(query, pedido.class).build();

        mAdapter = new productoadpater3(firestoreRecyclerOptions, this);
        mAdapter.notifyDataSetChanged();
        recy.setAdapter(mAdapter);

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