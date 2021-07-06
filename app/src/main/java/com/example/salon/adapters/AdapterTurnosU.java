package com.example.salon.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salon.Producto;
import com.example.salon.R;
import com.example.salon.Turno;
import com.example.salon.database.clsWebRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

public class AdapterTurnosU extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private ArrayList<Turno> listaTurno;
    private OnNoteListener monNoteListener;

    public AdapterTurnosU(Activity context, ArrayList<Turno> listaTurno,OnNoteListener onNoteListener){
        this.context = context;
        this.monNoteListener = onNoteListener;
        this.listaTurno = listaTurno;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.layout_turno,parent,false);
        AdapterTurnosU.MiViewHolder vh = new AdapterTurnosU.MiViewHolder(v,monNoteListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MiViewHolder mvh = (MiViewHolder) holder;
        final Turno turno = this.listaTurno.get(position);
        String titulo = turno.getNombre() + " " + turno.getApellido() +": " + turno.getCorte();
        mvh.tvNombre.setText(titulo);
        mvh.tvFecha.setText(turno.getFecha());
        mvh.tvHora.setText(turno.getHora());
    }

    @Override
    public int getItemCount() {
        return this.listaTurno.size();
    }




    public static class MiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvNombre, tvFecha,tvHora;
        View myView;
        OnNoteListener onNoteListener;

        public MiViewHolder(@NonNull View itemView,OnNoteListener onNoteListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
            this.myView = itemView;
            this.tvNombre = (TextView) itemView.findViewById(R.id.tvTurnoNombreUsuario);
            this.tvFecha = (TextView) itemView.findViewById(R.id.tvTurnoFechaUsuario);
            this.tvHora = (TextView) itemView.findViewById(R.id.tvTurnoHoraUsuario);

        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }


}
