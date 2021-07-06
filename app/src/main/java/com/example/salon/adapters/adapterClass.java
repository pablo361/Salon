package com.example.salon.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.salon.producto.ActivityProductDetail;
import com.example.salon.Producto;
import com.example.salon.R;

import java.util.ArrayList;

public class adapterClass  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Activity context;
    public ArrayList<Producto> listaProd;

    public adapterClass(Activity contexto, ArrayList<Producto> lista){
        this.context = contexto;
        this.listaProd = lista;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.layout_elemento,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder mvh = (MyViewHolder) holder;
        final  Producto prod = this.listaProd.get(position);
        mvh.tvNombre.setText(prod.getNombre());
        Glide
                .with(context)
                .load(prod.getUrl())
                .apply(new RequestOptions().override(600, 600))
                .into(mvh.ivProd);
                //.load(prod.getUrl()).into(mvh.ivProd);


        mvh.ivProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityProductDetail.class);
                intent.putExtra("idProducto",listaProd.get(position).getIdProducto());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listaProd.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombre;
        ImageView ivProd;
        View contenedor;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.contenedor = itemView;
            tvNombre = (TextView) itemView.findViewById(R.id.textView);
            ivProd = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }


}
