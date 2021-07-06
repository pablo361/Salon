package com.example.salon.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salon.Pedidos;
import com.example.salon.R;
import com.example.salon.Turno;

import java.util.ArrayList;

public class AdapterPedidos extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private ArrayList<Pedidos> listaPedidos;
    private OnPedListener miPedListener;

    public AdapterPedidos(Activity context, ArrayList<Pedidos> listaPedidos,OnPedListener onPedListener){
        this.context = context;
        this.listaPedidos = listaPedidos;
        this.miPedListener = onPedListener;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.layout_pedido,parent,false);
        AdapterPedidos.MiViewHolder vh = new AdapterPedidos.MiViewHolder(v,miPedListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MiViewHolder mvh = (MiViewHolder) holder;
        final Pedidos ped = listaPedidos.get(position);
        String titulo = "Nombre: " + ped.getNombre();
        String id = "Pedido Nº: " + ped.getId();
        String fecha = "Fecha: " + ped.getFecha();
        mvh.tvNombre.setText(titulo);
        mvh.tvFecha.setText(fecha);
        mvh.tvId.setText(id);
    }

    @Override
    public int getItemCount() {
        return this.listaPedidos.size();
    }




    public static class MiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvNombre, tvFecha,tvId;
        View myView;
        OnPedListener onPedListener;


        public MiViewHolder(@NonNull View itemView,OnPedListener onPedListener) {
            super(itemView);
            this.myView = itemView;
            this.onPedListener = onPedListener;
            this.tvNombre = (TextView) itemView.findViewById(R.id.tvNombrePedido);
            this.tvFecha = (TextView) itemView.findViewById(R.id.tvFechaPedido);
            this.tvId = (TextView) itemView.findViewById(R.id.ttvPedidoNum);
            myView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.onPedListener.onPedClick(getAdapterPosition());
        }
    }

    public interface OnPedListener{
        void onPedClick(int position);
    }




}
