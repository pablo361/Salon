package com.example.salon;

public class Pedidos {
    String id,nombre,fecha;

    public Pedidos(String id,String nombre,String fecha){
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }
}
