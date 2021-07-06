package com.example.salon.producto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.salon.MainActivity;
import com.example.salon.Producto;
import com.example.salon.R;
import com.example.salon.adapters.adapterClass;
import com.example.salon.database.clsWebRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ActivityProductTrat extends MainActivity {
    ArrayList<Producto> listaProductos;
    RecyclerView rv;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_productos);
        init();
        String idtipo = sharedPreferences.getString("tipoUsuarioId","");
        ImageButton b = (ImageButton) findViewById(R.id.imagebuttonCarrito);
        if (idtipo.equals("1")){
            b.setVisibility(View.GONE);
        }
        this.context = this;
        this.activity = this;
        this.listaProductos = new ArrayList<>();
        String idProd = sharedPreferences.getString("idTratamiento","");
        String title = sharedPreferences.getString("tratNombre","");
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        TextView tvt = (TextView) toolbar.findViewById(R.id.toolbar_title);
        tvt.setText(title);
        RequestParams params = new RequestParams();
        params.add("type","getTratProd");
        params.add("idTratProducto",idProd);
        clsWebRequest.post(this,"apiSalon.php",params, new ActivityProductTrat.ResponseHandler());
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
                JSONArray jAray = response.getJSONArray("productos");
                for (int i = 0;i<jAray.length();i++){
                    JSONObject jo = jAray.getJSONObject(i);
                    String idp = jo.getString("idProducto");
                    String nombrep = jo.getString("nombre");
                    String imagen = jo.getString("imagenurl");
                    String precio =jo.getString("precio");
                    Producto art = new Producto(idp,nombrep,precio,imagen);
                    listaProductos.add(art);

                }
                rv = (RecyclerView) findViewById(R.id.objRecyclerViewTP);
                rv.setHasFixedSize(true);
                layoutManager = new GridLayoutManager(context,2);
                rv.setLayoutManager(layoutManager);
                adapter = new adapterClass(activity,listaProductos);
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