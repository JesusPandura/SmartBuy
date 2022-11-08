package com.example.smartbuy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbuy.adapter.productoadpater4;
import com.example.smartbuy.modelo.usuario;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Mostrarventa extends AppCompatActivity {
    RecyclerView recy;
    productoadpater4 mAdapter;
    FirebaseFirestore mfirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarventa);

        recy =  findViewById(R.id.lalo);

        recy.setLayoutManager(new LinearLayoutManager(this));

        mfirestore = FirebaseFirestore.getInstance();
        Query query = mfirestore.collection("venta");

        FirestoreRecyclerOptions<usuario> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<usuario>().setQuery(query,usuario.class).build();

        mAdapter = new productoadpater4(firestoreRecyclerOptions, this);
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