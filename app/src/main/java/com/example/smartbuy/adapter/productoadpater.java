package com.example.smartbuy.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbuy.AgregarP;
import com.example.smartbuy.R;
import com.example.smartbuy.modelo.producto;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class productoadpater extends FirestoreRecyclerAdapter<producto,  productoadpater.ViewHolder> {

    private FirebaseFirestore  mfirestore = FirebaseFirestore.getInstance();
    Activity activity;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public productoadpater(@NonNull FirestoreRecyclerOptions<producto> options, Activity activity) {

        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull producto producto) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAbsoluteAdapterPosition());
        final String id = documentSnapshot.getId();

        holder.nombree.setText(producto.getNombre());
        holder.precioo.setText(producto.getPrecio());
        holder.cantidadd.setText(producto.getCantidad());
        holder.fechacc.setText(producto.getFechaC());
        holder.btn_modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i =new Intent(activity, AgregarP.class) ;
                    i.putExtra("id_productos",id);
                    activity.startActivity(i);
            }
        });

        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteproducto(id);
            }
        });
    }

    private void deleteproducto(String id) {
        mfirestore.collection("productos").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Exito al eliminar", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_product_single, viewGroup,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombree,precioo,cantidadd,fechacc;
        ImageButton btn_eliminar,btn_modificar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombree = itemView.findViewById(R.id.nombrep);
            precioo = itemView.findViewById(R.id.preciop);
            cantidadd = itemView.findViewById(R.id.cantidadp);
            fechacc = itemView.findViewById(R.id.fechacp);
            btn_eliminar = itemView.findViewById(R.id.btn_eliminarp);
            btn_modificar= itemView.findViewById(R.id.btn_modificard);
        }
    }
}
