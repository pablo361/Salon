package com.example.salon;

public class Turno {

    private String id,nombre,apellido,fecha,hora,corte;
    private Cliente cliente;

    public Turno(String id,String nombre,String apellido,String fecha, String hora ,String corte,Cliente cliente){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = fecha;
        this.hora = hora;
        this.corte = corte;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorte() {
        return corte;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
