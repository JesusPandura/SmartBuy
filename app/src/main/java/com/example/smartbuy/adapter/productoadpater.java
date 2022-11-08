package com.example.smartbuy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbuy.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.example.smartbuy.modelo.producto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class productoadpater extends FirestoreRecyclerAdapter<producto,  productoadpater.ViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public productoadpater(@NonNull FirestoreRecyclerOptions<producto> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull producto producto) {
        holder.nombree.setText(producto.getNombre());
        holder.precioo.setText(producto.getPrecio());
        holder.cantidadd.setText(producto.getCantidad());
        holder.fechacc.setText(producto.getFechaC());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_product_single, viewGroup,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombree,precioo,cantidadd,fechacc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombree = itemView.findViewById(R.id.nombrep);
            precioo = itemView.findViewById(R.id.preciop);
            cantidadd = itemView.findViewById(R.id.cantidadp);
            fechacc = itemView.findViewById(R.id.fechacp);
        }
    }
}
