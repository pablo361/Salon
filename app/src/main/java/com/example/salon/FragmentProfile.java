package com.example.salon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.salon.logIn.activityLogIn;
import com.example.salon.turnos.ActivityHistorial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {

    TextView tvNombreP, tvEmailP;
    Button bCerrarSesion,bHistorial,bPedidos;
    View vPrincipal;
    Context context;
    Activity activity;

    public SharedPreferences sharedPreferences;
    public String SHARED_PREF_NAME ="user_pref";
    public SharedPreferences.Editor sharedPrefEditor;


    public FragmentProfile() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.vPrincipal = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        crearViews();

        return vPrincipal;
    }

    protected void init() {
        this.context = getContext();
        this.activity = getActivity();
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);

        sharedPrefEditor = sharedPreferences.edit();

    }

    public void crearViews() {
        this.tvNombreP = (TextView) vPrincipal.findViewById(R.id.tvNombreP);
        this.tvEmailP = (TextView) vPrincipal.findViewById(R.id.tvEmailP);
        this.bCerrarSesion = (Button) vPrincipal.findViewById(R.id.buttonCerrarSesion);
        String nombre = sharedPreferences.getString("nombre", "");
        String nombreFull = nombre + " " + sharedPreferences.getString("apellido","");
        String mail = sharedPreferences.getString("email","");
        this.tvNombreP.setText(nombreFull);
        this.tvEmailP.setText(mail);
        this.bCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefEditor.putBoolean("logeado",false);
                sharedPrefEditor.apply();
                sharedPrefEditor.commit();
                Intent in = new Intent(context, activityLogIn.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
            }
        });
        this.bHistorial = (Button) vPrincipal.findViewById(R.id.bHistorial);
        this.bHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ActivityHistorial.class);
                startActivity(i);
            }
        });
        this.bPedidos = (Button) vPrincipal.findViewById(R.id.bHPedidos);
        this.bPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ActivityHistorialPedido.class);
                startActivity(intent);
            }
        });
    }


}