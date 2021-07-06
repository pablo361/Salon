package com.example.salon.producto;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.salon.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FragmentProductoPrincipal extends Fragment {

    Context context;
    Chip chip1 , chip2;
    ChipGroup myCG;
    View v;



    public FragmentProductoPrincipal() {
        // Required empty public constructor
    }

    public static FragmentProductoPrincipal newInstance() {
        FragmentProductoPrincipal fragment = new FragmentProductoPrincipal();
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
        v = inflater.inflate(R.layout.fragment_producto_principal, container, false);
        this.context = getContext();
        myCG = (ChipGroup) this.v.findViewById(R.id.chip_group);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentResultado frag = new FragmentResultado();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentInicioProducto, frag);
        transaction.commit();
        myCG.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (checkedId){
                    case R.id.chip1:
                        FragmentResultado fragment2 = new FragmentResultado();
                        transaction.replace(R.id.fragmentInicioProducto, fragment2);
                        transaction.commit();
                        break;
                    case R.id.chip2:
                        FragmentCategorias fragment = new FragmentCategorias();
                        transaction.replace(R.id.fragmentInicioProducto, fragment);
                        transaction.commit();
                        break;
                    case R.id.chip3:
                        FragmentProductos fragmen3t = new FragmentProductos();
                        transaction.replace(R.id.fragmentInicioProducto, fragmen3t);
                        transaction.commit();
                        break;

                }
            }
        });
    }

    /*public void selectChip(){
        myCG = (ChipGroup) this.v.findViewById(R.id.chip_group);
        myCG.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                String msg = Integer.toString(checkedId);
                Toast.makeText(context,"chip " + msg,Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (checkedId){
                    case R.id.chip1:
                        FragmentCategorias fragment2 = new FragmentCategorias();
                        transaction.replace(R.id.fragmentInicioProducto, fragment2);
                        transaction.commit();
                        break;
                    case R.id.chip2:
                        FragmentResultado fragment = new FragmentResultado();
                        transaction.replace(R.id.fragmentInicioProducto, fragment);
                        transaction.commit();
                        break;
                    default:
                        FragmentProductos pf = new FragmentProductos();
                        transaction.replace(R.id.fragmentInicioProducto,pf);
                        transaction.commit();

                }
            }
        });
    }*/


}