package com.example.salon.producto;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.salon.Producto;
import com.example.salon.R;
import com.example.salon.adapters.adapterClass;
import com.example.salon.database.clsWebRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProductos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProductos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ArrayList<Producto> productosLista = new ArrayList<>();
    RecyclerView rv;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;
    Activity activity;
    public View vista;

    public SharedPreferences sharedPreferences;
    public String SHARED_PREF_NAME ="user_pref";
    public SharedPreferences.Editor sharedPrefEditor;

    public FragmentProductos() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentProductos newInstance() {
        FragmentProductos fragment = new FragmentProductos();
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
        this.vista = inflater.inflate(R.layout.fragment_productos, container, false);
        return this.vista;
    }

    protected void init() {
        this.context = getContext();
        this.activity = getActivity();
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME,context.MODE_PRIVATE);

        sharedPrefEditor = sharedPreferences.edit();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        RequestParams param = new RequestParams();
        param.add("type","getProductos");
        clsWebRequest.post(context,"apiSalon.php",param,new FragmentProductos.ResponseHandler());
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
                    String precio = jo.getString("precio");
                    Producto art = new Producto(idp,nombrep,precio,imagen);
                    productosLista.add(art);

                }
                rv = (RecyclerView) vista.findViewById(R.id.objRecyclerView);
                rv.setHasFixedSize(true);
                layoutManager = new GridLayoutManager(context,2);
                rv.setLayoutManager(layoutManager);
                adapter = new adapterClass(activity,productosLista);
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