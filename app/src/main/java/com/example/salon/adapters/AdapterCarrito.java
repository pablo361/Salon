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

public class AdapterCarrito extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity activity;
    ArrayList<Producto> listaProductos;

    public SharedPreferences sharedPreferences;
    public String SHARED_PREF_NAME ="user_pref";
    public SharedPreferences.Editor sharedPrefEditor;

    public AdapterCarrito(Activity activity,ArrayList<Producto> listaProductos){
        this.activity = activity;
        this.listaProductos = listaProductos;
        init();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.layout_carrito_window,parent,false);
        return new AdapterCarrito.MiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final AdapterCarrito.MiViewHolder mvh = (AdapterCarrito.MiViewHolder) holder;
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
        mvh.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("¿Borrar Elemento?");
                builder.setMessage("¿Desea quitar este producto de su carrito?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                        String idUsuario = sharedPreferences.getString("id","");
                        RequestParams param = new RequestParams();
                        param.add("usuarioID",idUsuario);
                        param.add("productoID",prod.getIdProducto());
                        param.add("type","borrarCarrito");
                        clsWebRequest.post(activity,"apiSalon.php",param, new JsonHttpResponseHandler());

                        activity.finish();
                        activity.startActivity(activity.getIntent());
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                builder.show();
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

        }
    }
}
