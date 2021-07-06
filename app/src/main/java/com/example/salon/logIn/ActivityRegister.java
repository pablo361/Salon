package com.example.salon.logIn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.salon.ActivityAver;
import com.example.salon.MainActivity;
import com.example.salon.Producto;
import com.example.salon.R;
import com.example.salon.activityHome;
import com.example.salon.database.clsWebRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class ActivityRegister extends MainActivity {

    EditText etNombre,etApe,etDni,etContra,etEmail;
    Context context;
    Activity activity;
    Button bAceptar, bCancelar;
    String nombre,apellido,mail,dni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        this.activity = this;
        crearViews();
        setBotones();
    }

    @Override
    protected void init() {
        super.init();
        this.context = this;
    }

    public void crearViews(){
        this.etNombre = (EditText) findViewById(R.id.editTextNombreR);
        this.etApe = (EditText) findViewById(R.id.editTextApellidoR);
        this.etContra = (EditText) findViewById(R.id.editTextTextPassword);
        this.etDni = (EditText) findViewById(R.id.editTextDNIR);
        this.etEmail = (EditText) findViewById(R.id.editTextTextEmailR);
    }



    public void setBotones(){
        this.bAceptar = (Button) findViewById(R.id.bAceptarR);
        this.bCancelar = (Button) findViewById(R.id.bCancelarR);
        this.bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = etNombre.getText().toString();
                apellido = etApe.getText().toString();
                dni = etDni.getText().toString();
                String password = etContra.getText().toString();
                mail = etEmail.getText().toString();


                RequestParams params = new RequestParams();
                params.add("type", "registrar");
                params.add("nombre", nombre);
                params.add("apellido", apellido);
                params.add("idTipoUsuario","2");
                params.add("dni",dni);
                params.add("email",mail);
                params.add("password",password);
                clsWebRequest.post(context, "apiSalon", params, new ActivityRegister.ResponseHandler());
            }
        });
        this.bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(context,activityLogIn.class);
        startActivity(a);
    }

    private class ResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                if (response.getBoolean("error")) {
                    // error de login
                }else {
                    String jo = response.getString("idUsu");
                    //String id = jo.getString("id");
                    sharedPrefEditor.putString("id", jo);
                    sharedPrefEditor.putString("email", mail);
                    sharedPrefEditor.putString("nombre", nombre);
                    sharedPrefEditor.putString("apellido",apellido);
                    sharedPrefEditor.putBoolean("logeado", true);
                    sharedPrefEditor.putString("tipoUsuarioId","2");
                    sharedPrefEditor.apply();
                    sharedPrefEditor.commit();
                    Intent i = new Intent(context, activityHome.class);
                    startActivity(i);
                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Log.d("MENSAJEMIO::1", "Error  Ago anda mal che " + statusCode);
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    }


}