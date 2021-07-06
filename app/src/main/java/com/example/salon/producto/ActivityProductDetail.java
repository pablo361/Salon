package com.example.salon.producto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.salon.MainActivity;
import com.example.salon.Producto;
import com.example.salon.R;
import com.example.salon.database.clsWebRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class ActivityProductDetail extends MainActivity {
    String idProducto,idUsuario;

    TextView tvPDN,tvPDP;
    ImageView ivPD;
    Context context;
    private Producto producto;
    Button botonAgregarCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        init();

        this.context = this;
        Intent intent = getIntent();
        this.idProducto = intent.getExtras().getString("idProducto");
        idUsuario = sharedPreferences.getString("id","");
        createElements();
        crearSolicitud();

    }

    public void createElements(){
        this.tvPDN = (TextView) findViewById(R.id.textViewProductoDetalleN);
        this.tvPDP = (TextView) findViewById(R.id.textViewPrecioProductoP);
        this.ivPD = (ImageView) findViewById(R.id.imageViewDP);

        this.botonAgregarCarrito = (Button) findViewById(R.id.botonAgregarCarrito);
        String idtipo = sharedPreferences.getString("tipoUsuarioId","");
        ImageButton b = (ImageButton) findViewById(R.id.imagebuttonCarrito);
        if (idtipo.equals("1")){
            b.setVisibility(View.GONE);
            botonAgregarCarrito.setVisibility(View.GONE);
        }
    }

    public void agregarACarrito(View view){
        RequestParams param = new RequestParams();
        param.add("type","agregarACarrito");
        param.add("idProducto",idProducto);
        param.add("idUsuario",idUsuario);
        Toast.makeText(context,"Producto agregado a su carrito",Toast.LENGTH_SHORT).show();
        clsWebRequest.post(this,"apiSalon.php",param,new JsonHttpResponseHandler());
    }

    public void crearSolicitud(){
        RequestParams param = new RequestParams();
        param.add("type","getDetalleProducto");
        param.add("idProducto",idProducto);
        clsWebRequest.post(this,"apiSalon.php",param,new ActivityProductDetail.ResponseHandler());
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
                if (response.getBoolean("error")) {
                    // error de login
                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    // login exito
                    JSONObject user = response.getJSONObject("prod");
                    String nombre,image,precio;
                    nombre = user.getString("nombre");
                    image = user.getString("imagenurl");
                    precio = "$" + user.getString("precio");
                    tvPDN.setText(nombre);
                    tvPDP.setText(precio);
                    Glide.with(context)
                            .load(image).into(ivPD);


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