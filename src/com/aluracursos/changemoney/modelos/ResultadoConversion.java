package com.aluracursos.changemoney.modelos;

import com.aluracursos.changemoney.dto.*;

public class ResultadoConversion extends DatosConversion {
    private double montoConvertido;

    public ResultadoConversion(String monedaOrigen, int monto) {
        super(monedaOrigen, monto);
        this.montoConvertido = 0.0;
    }

    //Setters y getters

    public void setMontoConvertido(double montoConvertido) {
        this.montoConvertido = montoConvertido;
    }

    @Override
    public String toString() {
        return "ResultadoConversion{" +
                "idExchange=" + this.getIdExchange() +
                ", idMonedaOrigen=" + this.getIdMonedaOrigen() +
                ", idMonedaDestino=" + this.getIdMonedaDestino() +
                ", monedaOrigen='" + this.getMonedaOrigen() + '\'' +
                ", monedaDestino='" + this.getMonedaDestino() + '\'' +
                ", monto=" + this.getMonto() +
                ", montoConvertido=" + this.montoConvertido +
                '}';
    }
}
