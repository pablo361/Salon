package com.example.salon;

public class Cliente {
    private String idU,nombre,apellido,email,dni;

    public Cliente(String id,String nombre,String apellido,String email,String dni){
        this.idU = id;this.nombre = nombre;this.apellido = apellido;this.email = email;this.dni = dni;
    }

    public String getIdU() {
        return idU;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getEmail() {
        return email;
    }
}
