package com.aluracursos.changemoney.modelos;

import com.aluracursos.changemoney.utilerias.Constantes;

public class DatosConversion {
    private int idExchange;
    private int idMonedaOrigen;
    private int idMonedaDestino;
    private String monedaOrigen;
    private String monedaDestino;    //ejemplo:  USD   MXN
    private int monto;


    public DatosConversion(String monedaOrigen, int monto) {
        this.monedaOrigen = monedaOrigen;
        this.idMonedaOrigen = this.obtenIdMonedaOrigen(this.monedaOrigen);
        this.monto = monto;
    }

    private int obtenIdMonedaOrigen(String monedaOrigen) {
        for (int i = 0; i < Constantes.listaMonedas.length; i++) {
            if (monedaOrigen != null && Constantes.listaMonedas[i].equals(monedaOrigen)) {
                return i;
            }
        }
        return -1;
    }

    //getters y setters

    public void setIdExchange(int idExchange) {
        this.idExchange = idExchange;
    }

    public void setIdMonedaDestino(int idMonedaDestino) {
        this.idMonedaDestino = idMonedaDestino;
        this.monedaDestino = Constantes.listaMonedas[this.idMonedaDestino];
    }

    public int getIdExchange() {
        return idExchange;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public int getMonto() {
        return monto;
    }

    public int getIdMonedaOrigen() {
        return idMonedaOrigen;
    }

    public int getIdMonedaDestino() {
        return idMonedaDestino;
    }

    @Override
    public String toString() {
        return "DatosConversion{" +
                "idExchange=" + idExchange +
                ", idMonedaOrigen=" + idMonedaOrigen +
                ", idMonedaDestino=" + idMonedaDestino +
                ", monedaOrigen='" + monedaOrigen + '\'' +
                ", monedaDestino='" + monedaDestino + '\'' +
                ", monto=" + monto +
                '}';
    }
}
