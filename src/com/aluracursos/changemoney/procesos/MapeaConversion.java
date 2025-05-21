package com.aluracursos.changemoney.procesos;

import com.aluracursos.changemoney.dto.abstractapi.AbstractApiDto;
import com.aluracursos.changemoney.dto.currencyapi.RatesCurrencyApiDto;
import com.aluracursos.changemoney.dto.currencyapi.RespuestaCurrencyApiDto;
import com.aluracursos.changemoney.dto.currencylayer.RespuestaCurrencyLayerDto;
import com.aluracursos.changemoney.dto.exchangerate.ExChangeRateDto;
import com.aluracursos.changemoney.dto.exconvert.RespuestaExConvertDto;
import com.aluracursos.changemoney.dto.freecurrencyapi.RespuestaFreeCurrencyApiDto;
import com.aluracursos.changemoney.utilerias.Constantes;

public class MapeaConversion {
    //--Orden de las monedas:  {"MXN", "USD", "EUR", "JPY", "GBP", "RUB", "BRL", "CAD", "CYN", "INR"};
    //--Orden de los exhanges: {"exChangeRate", "freeCurrencyApi", "exConvert", "currencyLayer", "abstractApi", "currencyApi"};
    private double montoConvertido = Constantes.CI_ERROR_SERVICE;  //indica que no es soportado ese tipo de moneda o existe algun error

    public MapeaConversion() {
        montoConvertido = Constantes.CI_ERROR_SERVICE;
    }

    public MapeaConversion(ExChangeRateDto info) {
        if ( info != null && info.result().equals("success")) {
            //rmv: System.out.println("ExChangeRateDto: " + info);
            this.montoConvertido = info.conversion_result();
        }
    }

    public MapeaConversion(RespuestaFreeCurrencyApiDto info) {
        if ( info != null && info.data() != null) {
            //rmv: System.out.println("RespuestaFreeCurrencyApiDto: " + info);
            this.montoConvertido = info.data().MXN();
        }
    }

    public MapeaConversion(RespuestaExConvertDto info) {
        if ( info != null && info.result() != null && info.result() != null) {
            //rmv: System.out.println("RespuestaExConvertDto: " + info);
            this.montoConvertido = info.result().rate() * info.amount();
        }
    }

    public MapeaConversion(RespuestaCurrencyLayerDto info) {
        if ( info != null && info.success()) {
            //rmv: System.out.println("CurrencyLayerDto: " + info);
            this.montoConvertido = info.result();
        }
    }

    public MapeaConversion(AbstractApiDto info) {
        if ( info != null) {
            //rmv: System.out.println("AbstractApiDto: " + info);
            this.montoConvertido = info.converted_amount();
        }
    }

    public MapeaConversion(RespuestaCurrencyApiDto info) {
        if ( info != null && info.rates() != null && info.status().equals("success")) {
            //rmv: System.out.println("RespuestaCurrencyApiDto: " + info);
            this.montoConvertido = obtenRatesCurrencyApi(info.rates());  //-- info.rates().GBP().rate_for_amount();
        }
    }

    public MapeaConversion(String nada) {
        //rmv: System.out.println("CurrencyApiDto. Exchange implementado en futuras versiones.");
        this.montoConvertido = 0.0;
    }

    private double obtenRatesCurrencyApi(RatesCurrencyApiDto rates) {
        if ( rates.MXN() != null ) {
            return rates.MXN().rate_for_amount();
        } else if ( rates.USD() != null ) {
            return rates.USD().rate_for_amount();
        } else if ( rates.EUR() != null ) {
            return rates.EUR().rate_for_amount();
        } else if ( rates.JPY() != null ) {
            return rates.JPY().rate_for_amount();
        } else if ( rates.GBP() != null ) {
            return rates.GBP().rate_for_amount();
        } else if ( rates.RUB() != null ) {
            return rates.RUB().rate_for_amount();
        } else if ( rates.BRL() != null ) {
            return rates.BRL().rate_for_amount();
        } else if ( rates.CAD() != null ) {
            return rates.CAD().rate_for_amount();
        } else if ( rates.CYN() != null ) {
            return rates.CYN().rate_for_amount();
        } else if ( rates.CNY() != null ) {
            return rates.CNY().rate_for_amount();
        } else if ( rates.INR() != null ) {
            return rates.INR().rate_for_amount();
        }
        return 0.0;
    }
    //getter

    public double getMontoConvertido() {
        return this.montoConvertido;
    }
}
