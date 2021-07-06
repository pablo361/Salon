package com.example.salon.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.salon.Producto;
import com.example.salon.R;
import com.example.salon.database.clsWebRequest;
import com.example.salon.producto.ActivityProductDetail;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

public class AdapterDetallePedidos extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity activity;
    ArrayList<Producto> listaProductos;

    public SharedPreferences sharedPreferences;
    public String SHARED_PREF_NAME ="user_pref";
    public SharedPreferences.Editor sharedPrefEditor;

    public AdapterDetallePedidos(Activity activity,ArrayList<Producto> listaProductos){
        this.activity = activity;
        this.listaProductos = listaProductos;
        init();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.layout_carrito_window,parent,false);
        return new AdapterDetallePedidos.MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final AdapterDetallePedidos.MiViewHolder mvh = (AdapterDetallePedidos.MiViewHolder) holder;
        final  Producto prod = this.listaProductos.get(position);
        mvh.tvN.setText(prod.getNombre());
        mvh.tvD.setText(prod.getPrecio());
        Glide.with(activity)
                .load(prod.getUrl()).into(mvh.iv);
        mvh.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityProductDetail.class);
                intent.putExtra("idProducto",listaProductos.get(position).getIdProducto());
                activity.startActivity(intent);
            }
        });
    }

    public void init(){
        sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        sharedPrefEditor = sharedPreferences.edit();
    }

    @Override
    public int getItemCount() {
        return this.listaProductos.size();
    }

    public static class MiViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView tvN,tvD;
        ImageButton ibDelete;
        View contenedor;

        public MiViewHolder(@NonNull View itemView) {
            super(itemView);
            this.contenedor = itemView;
            this.iv = (ImageView) contenedor.findViewById(R.id.imageViewCarrito);
            this.tvN = (TextView) contenedor.findViewById(R.id.textViewCarritoN);
            this.tvD = (TextView) contenedor.findViewById(R.id.textViewCarritoP);
            this.ibDelete = (ImageButton) contenedor.findViewById(R.id.borrarCarrito);
            this.ibDelete.setVisibility(View.GONE);

        }
    }
}
