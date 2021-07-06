package com.example.salon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salon.adapters.AdapterCarrito;
import com.example.salon.adapters.AdapterDetallePedidos;
import com.example.salon.adapters.AdapterTurnosU;
import com.example.salon.database.clsWebRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ActivityPedidoDetalle extends MainActivity {
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    String idUsuario;
    Activity activity;
    int total;
    TextView tvTotal;
    ArrayList<Producto> listaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        TextView toolbatTV = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbatTV.setText("Detalle");
        ImageButton bCarrito = (ImageButton) toolbar.findViewById(R.id.imagebuttonCarrito);
        bCarrito.setVisibility(View.GONE);
        Intent i = getIntent();
        String idpedido = i.getExtras().getString("idPedido");
        this.listaProductos = new ArrayList<>();
        init();
        this.tvTotal = (TextView) findViewById(R.id.totalCarrito);

        this.total = 0;
        this.idUsuario = sharedPreferences.getString("id","");
        RequestParams param = new RequestParams();
        param.add("type","getCarritoPedido");
        param.add("idPedido",idpedido);
        clsWebRequest.post(context,"apiSalon.php",param,new ActivityPedidoDetalle.ResponseHandler());
        crearBoton();
    }

    public void crearBoton(){
        Button botonPedido = (Button) findViewById(R.id.botonPedido);
        botonPedido.setVisibility(View.GONE);

    }

    @Override
    protected void init() {
        super.init();
        this.activity = this;
        context = this;
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
                JSONArray jAray = response.getJSONArray("productos");
                for (int i = 0;i<jAray.length();i++){
                    JSONObject jo = jAray.getJSONObject(i);
                    String nombre,url,id,precio;
                    nombre = jo.getString("nombre");
                    url = jo.getString("imagenurl");
                    id = jo.getString("idProducto");;
                    precio = jo.getString("precio");
                    total = total + Integer.parseInt(precio);
                    Producto p = new Producto(id,nombre,precio,url);
                    listaProductos.add(p);
                }
                String preciof = "Total: $" + total;
                tvTotal.setText(preciof);

                rv = (RecyclerView) findViewById(R.id.objRecyclerViewC);
                rv.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(context);
                rv.setLayoutManager(layoutManager);
                adapter = new AdapterDetallePedidos(activity,listaProductos);
                rv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (listaProductos.size() == 0) tvTotal.setVisibility(View.GONE);
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