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

import com.example.salon.R;


public class FragmentResultado extends Fragment implements View.OnClickListener{
    ImageButton b1,b2,b3,b4,b5,b6;
    public SharedPreferences sharedPreferences;
    public String SHARED_PREF_NAME ="user_pref";
    public SharedPreferences.Editor sharedPrefEditor;
    Context context;
    Activity activity;
    String tratId;



    public FragmentResultado() {
        // Required empty public constructor
    }

    public static FragmentResultado newInstance() {
        FragmentResultado fragment = new FragmentResultado();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_resultado, container, false);
        this.b1 = v.findViewById(R.id.bpC);
        this.b2 = v.findViewById(R.id.bpF);
        this.b3 = v.findViewById(R.id.bpCa);
        this.b4 = v.findViewById(R.id.bpB);
        this.b5 = v.findViewById(R.id.bpR);
        this.b6 = v.findViewById(R.id.bpRi);
        this.b1.setOnClickListener(this);
        this.b2.setOnClickListener(this);
        this.b3.setOnClickListener(this);
        this.b4.setOnClickListener(this);
        this.b5.setOnClickListener(this);
        this.b6.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        String tratNombre = "";
        switch (v.getId()){
            case R.id.bpC:
                tratId = "1";
                tratNombre = "Caspa";
                break;
            case R.id.bpF:
                tratId = "2";
                tratNombre = "Frizz";
                break;
            case R.id.bpCa:
                tratNombre = "Caida";
                tratId = "3";
                break;
            case R.id.bpB:
                tratId = "4";
                tratNombre = "Brillo";
                break;
            case R.id.bpR:
                tratId = "5";
                tratNombre = "Reparacion";
                break;
            case R.id.bpRi:
                tratNombre = "Brillo";
                tratId = "6";
                break;
        }
        sharedPrefEditor.putString("idTratamiento",tratId);
        sharedPrefEditor.putString("tratNombre",tratNombre);
        sharedPrefEditor.apply();
        sharedPrefEditor.commit();
        Intent i = new Intent(context, ActivityProductTrat.class);
        startActivity(i);
    }

    protected void init() {
        this.context = getContext();
        this.activity = getActivity();
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);

        sharedPrefEditor = sharedPreferences.edit();

    }
}
