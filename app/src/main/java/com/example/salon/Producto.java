package com.example.salon;

import android.os.Parcel;
import android.os.Parcelable;

public class Producto  {
    private String idProducto,nombre,precio,url;

    public Producto(String idProducto,String nombre,String precio,String url){
        this.nombre = nombre;
        this.url = url;
        this.idProducto = idProducto;
        this.precio = precio;
    }


    public String getUrl() {
        return url;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getPrecio() {
        return precio;
    }
}
