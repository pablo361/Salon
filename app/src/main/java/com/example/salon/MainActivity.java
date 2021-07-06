package com.example.salon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

   public SharedPreferences sharedPreferences;
   public String SHARED_PREF_NAME ="user_pref";
   public SharedPreferences.Editor sharedPrefEditor;
   public Context context;
   public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void init() {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        sharedPrefEditor = sharedPreferences.edit();

    }

    public void irACarrito(View view){
        Intent i;
        String id = sharedPreferences.getString("tipoUsuarioId","");
        if (id.equals("1")){
            i = new Intent(this,ActivityPedidos.class);
        } else {
            i = new Intent(this,ActivityCarrito.class);
        }
        startActivity(i);
    }
}