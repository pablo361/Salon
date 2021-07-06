package com.example.salon.turnos;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.salon.Cliente;
import com.example.salon.MainActivity;
import com.example.salon.R;
import com.example.salon.Turno;
import com.example.salon.adapters.AdapterTurnosU;
import com.example.salon.database.clsWebRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ActivityHistorial extends MainActivity implements AdapterTurnosU.OnNoteListener{

    String email,nombre,apellido,fecha,hora,descripcion;
    ArrayList<Turno> listaTurno;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        MaterialToolbar mt = (MaterialToolbar) findViewById(R.id.toolbar);
        TextView tvToolvar = (TextView) findViewById(R.id.toolbar_title);
        tvToolvar.setText("Historial de Turnos");

        init();
        String idtipo = sharedPreferences.getString("tipoUsuarioId","");
        ImageButton b = (ImageButton) findViewById(R.id.imagebuttonCarrito);
        if (idtipo.equals("1")){
            b.setVisibility(View.GONE);
        }
        this.listaTurno = new ArrayList<>();
        this.email = sharedPreferences.getString("email","");
        RequestParams param = new RequestParams();
        param.add("type","getTodosTurnos");
        param.add("tipoUsuarioId",idtipo);
        param.add("emailT",email);
        clsWebRequest.post(context,"apiSalon.php",param,new ActivityHistorial.ResponseHandler());
    }

    @Override
    protected void init() {
        super.init();
        context = this;
        activity = this;
    }

    @Override
    public void onNoteClick(int position) {

    }

    private class ResponseHandler extends JsonHttpResponseHandler{
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                JSONArray jAray = response.getJSONArray("turnosUsuario");
                for (int i = 0;i<jAray.length();i++){
                    JSONObject jo = jAray.getJSONObject(i);
                    String id,nombre,apellido,fecha,fechaf,hora,descripcion;
                    id = jo.getString("idTurno");
                    nombre = jo.getString("nombre");
                    apellido = jo.getString("apellido");
                    fecha = jo.getString("fecha");
                    //fechaf = arreglarfecha(fecha);
                    descripcion = jo.getString("descripcion");
                    hora = jo.getString("hora").substring(0,5);
                    Cliente cliente = new Cliente(jo.getString("idusuario"),nombre,apellido,jo.getString("email"),jo.getString("dni"));
                    Turno turn = new Turno(id,nombre,apellido,fecha,hora,descripcion,cliente);
                    listaTurno.add(turn);
                }
                rv = (RecyclerView) findViewById(R.id.objRecyclerViewH);
                rv.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(context);
                rv.setLayoutManager(layoutManager);
                adapter = new AdapterTurnosU(activity,listaTurno, (AdapterTurnosU.OnNoteListener) activity);
                rv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            Log.d("IDM2020::4", "Error " + statusCode);
            super.onFailure(statusCode, headers, responseString, throwable);
        }

        @Override
        public void onFinish() {
            super.onFinish();


        }

    }
}