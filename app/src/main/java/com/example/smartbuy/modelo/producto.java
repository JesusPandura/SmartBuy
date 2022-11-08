package com.example.smartbuy.modelo;

public class producto {
    private String nombre,fechaC,precio, cantidad;



    public producto(){

    }

    public producto(String nombre, String fechaC, String precio, String cantidad) {
        this.nombre = nombre;
        this.fechaC = fechaC;
        this.precio = precio;
        this.cantidad = cantidad;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaC() {
        return fechaC;
    }

    public void setFechaCa(String fechaCa) {
        this.fechaC = fechaCa;
    }

    public String getPrecio() {
        return precio;
    }


    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }


}
