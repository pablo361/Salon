package com.example.salon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.salon.adapters.AdapterCarrito;
import com.example.salon.adapters.AdapterPedidos;
import com.example.salon.database.clsWebRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ActivityPedidos extends MainActivity implements AdapterPedidos.OnPedListener{

    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Activity activity;
    ArrayList<Pedidos> listaPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        TextView toolbatTV = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbatTV.setText("Pedidos");
        ImageButton bCarrito = (ImageButton) toolbar.findViewById(R.id.imagebuttonCarrito);
        bCarrito.setVisibility(View.GONE);
        this.listaPedidos = new ArrayList<>();
        init();
        RequestParams param = new RequestParams();
        param.add("type","getPedidos");
        clsWebRequest.post(context,"apiSalon.php",param,new ActivityPedidos.ResponseHandler());
    }

    protected void init() {
        super.init();
        this.activity = this;
        context = this;
    }

    @Override
    public void onPedClick(int position) {
        String idp = listaPedidos.get(position).getId();
        Intent i = new Intent(this,ActivityPedidoDetalle.class);
        i.putExtra("idPedido",idp);
        startActivity(i);
    }

    public class ResponseHandler extends JsonHttpResponseHandler {
        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                JSONArray jAray = response.getJSONArray("pedidos");
                for (int i = 0;i<jAray.length();i++){
                    JSONObject jo = jAray.getJSONObject(i);
                    String nombre,id,apellido,fecha;
                    apellido = jo.getString("apellido");
                    nombre = jo.getString("nombre") + " " + apellido;
                    id = jo.getString("idPedido");;
                    fecha = jo.getString("fecha");
                    Pedidos p = new Pedidos(id,nombre,fecha);
                    listaPedidos.add(p);
                }
                rv = (RecyclerView) findViewById(R.id.objRecyclerViewPed);
                rv.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(context);
                rv.setLayoutManager(layoutManager);
                adapter = new AdapterPedidos(activity,listaPedidos, (AdapterPedidos.OnPedListener) activity);
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