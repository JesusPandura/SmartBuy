package com.example.smartbuy.modelo;

public class pedido {

    private String correo,fecha,total,C_confianza, estado;
    int idpedido;

    public pedido() {

    }
    public pedido(String correo, String fecha, String total, int idpedido, String C_confianza, String estado) {
        this.correo = correo;
        this.fecha = fecha;
        this.total = total;
        this.idpedido = idpedido;
        this.C_confianza= C_confianza;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCorreo() {
        return correo;
    }

    public String getC_confianza() {
        return C_confianza;
    }

    public void setC_confianza(String c_confianza) {
        C_confianza = c_confianza;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }
}
