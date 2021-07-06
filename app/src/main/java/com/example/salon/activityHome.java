package com.example.salon;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.salon.producto.FragmentProductoPrincipal;
import com.example.salon.turnos.FragmentTurnos;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activityHome extends MainActivity {


    TextView tbName;
    ImageButton ibcarrito;
    Context context;
    Activity activity;
    MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        this.context = this;
        this.activity = this;
        this.ibcarrito = (ImageButton) findViewById(R.id.imagebuttonCarrito);
        //Toolbar toolbar = findViewById(R.id.toolbar);
         this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.tbName = (TextView) this.toolbar.findViewById(R.id.toolbar_title);
        BottomNavigationView bottomNavigationView = findViewById(R.id.boton_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        String idTipoUsuario = sharedPreferences.getString("tipoUsuarioId","");
        MenuItem menuItem;
        if (idTipoUsuario.equals("1")){
            //ibcarrito.setVisibility(View.GONE);
            menuItem = bottomNavigationView.getMenu().getItem(0);
        } else {
            menuItem = bottomNavigationView.getMenu().getItem(1);
        }
        navListener.onNavigationItemSelected(menuItem);
       menuItem.setChecked(true);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()){
                case R.id.botonProductosNav:
                    //FragmentCategorias fragment2 = new FragmentCategorias();
                    FragmentProductoPrincipal fragment2 = new FragmentProductoPrincipal();
                    transaction.replace(R.id.fragmentInicio, fragment2);
                    //getSupportActionBar().setTitle("Productos");
                    tbName.setText("Productos");
                    transaction.commit();
                    break;
                case R.id.botonTurnosNav:
                    FragmentTurnos fragment = new FragmentTurnos();
                    


                    transaction.replace(R.id.fragmentInicio, fragment);
                    //getSupportActionBar().setTitle("Turnos");
                    tbName.setText("Turnos");
                    transaction.commit();
                    break;
                case R.id.botonPerfilNav:
                    FragmentProfile fragment3 = new FragmentProfile();
                    transaction.replace(R.id.fragmentInicio, fragment3);
                    //getSupportActionBar().setTitle("Perfil");
                    tbName.setText("Perfil");
                    transaction.commit();
            }

            return true;
        }
    };

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }


}