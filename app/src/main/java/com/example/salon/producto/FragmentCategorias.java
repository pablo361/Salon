package com.example.salon.producto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.salon.ActivityCarrito;
import com.example.salon.R;


public class FragmentCategorias extends Fragment implements View.OnClickListener {

    private ImageButton boton1,boton2,boton3,boton4,boton5,boton6;
    public SharedPreferences sharedPreferences;
    public String SHARED_PREF_NAME ="user_pref";
    public SharedPreferences.Editor sharedPrefEditor;
    Context context;
    Activity activity;
    String tipoId;

    public FragmentCategorias() {
        // Required empty public constructor
    }


    public static FragmentCategorias newInstance() {
        FragmentCategorias fragment = new FragmentCategorias();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);
        init();
        this.boton1 = (ImageButton) view.findViewById(R.id.bp1);
        this.boton2 = (ImageButton) view.findViewById(R.id.bp2);
        this.boton3 = (ImageButton) view.findViewById(R.id.bp3);
        this.boton4 = (ImageButton) view.findViewById(R.id.bp4);
        this.boton5 = (ImageButton) view.findViewById(R.id.bp5);
        this.boton6 = (ImageButton) view.findViewById(R.id.bp6);
        this.boton2.setOnClickListener(this);
        this.boton3.setOnClickListener(this);
        this.boton4.setOnClickListener(this);
        this.boton5.setOnClickListener(this);
        this.boton6.setOnClickListener(this);
        this.boton1.setOnClickListener(this);
        return view;
    }


    protected void init() {
        this.context = getContext();
        this.activity = getActivity();
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);

        sharedPrefEditor = sharedPreferences.edit();

    }

    @Override
    public void onClick(View v) {
        String tratNombre = "";
        switch(v.getId()){
            case R.id.bp1:
                tipoId = "1";
                tratNombre = "Shampoo";
                break;
            case R.id.bp2:
                tipoId = "2";
                tratNombre = "Mascarilla";
                break;
            case R.id.bp3:
                tipoId = "3";
                tratNombre = "Acondicionador";
                break;
            case R.id.bp4:
                tipoId = "4";
                tratNombre = "Aceite";
                break;
            case R.id.bp5:
                tipoId = "5";
                tratNombre = "Tintura";
                break;
            case R.id.bp6:
                tipoId = "6";
                tratNombre = "Spray";
                break;
        }
        sharedPrefEditor.putString("idTipoProducto",tipoId);
        sharedPrefEditor.putString("tipoNombre",tratNombre);
        sharedPrefEditor.apply();
        sharedPrefEditor.commit();
        Intent i = new Intent(context, ActivityTipoProductos.class);
        startActivity(i);

    }
}