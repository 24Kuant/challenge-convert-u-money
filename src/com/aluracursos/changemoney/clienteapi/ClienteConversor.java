package com.aluracursos.changemoney.clienteapi;

import com.aluracursos.changemoney.dto.abstractapi.AbstractApiDto;
import com.aluracursos.changemoney.dto.currencyapi.RespuestaCurrencyApiDto;
import com.aluracursos.changemoney.dto.currencylayer.RespuestaCurrencyLayerDto;
import com.aluracursos.changemoney.dto.exchangerate.ExChangeRateDto;
import com.aluracursos.changemoney.dto.exconvert.RespuestaExConvertDto;
import com.aluracursos.changemoney.dto.freecurrencyapi.RespuestaFreeCurrencyApiDto;
import com.aluracursos.changemoney.exceptions.ErrorConsultaApiException;
import com.aluracursos.changemoney.modelos.ResultadoConversion;
import com.aluracursos.changemoney.procesos.MapeaConversion;
import com.aluracursos.changemoney.utilerias.Constantes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;

public class ClienteConversor {
    private static String[] urlsMoneyExchange = {
            "https://v6.exchangerate-api.com/v6/" + Constantes.keys[0] + "/pair/{monedaOrigen}/{monedasDestino}/{monto}",  //-- "https://v6.exchangerate-api.com/v6/6cd729a7324f5df6c253e846/latest/{monedaOrigen}",
            "https://api.freecurrencyapi.com/v1/latest?apikey=" + Constantes.keys[1] + "&currencies={monedasDestino}&base_currency={monedaOrigen}", //-- "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_kSgK2Y5QHukvrTszWLbgYyJqebMb1tqmEHAG0Goa&currencies={monedasDestino}&base_currency={monedaOrigen}",
            "https://api.exconvert.com/convert?access_key=" + Constantes.keys[2] + "&from={monedaOrigen}&to={monedasDestino}&amount={monto}", //-- "https://api.exconvert.com/fetchMulti?access_key=4d8c7372-001c9c88-d96eb95d-83b4c6ab&from={monedaOrigen}&to={monedasDestino}",
            "https://api.currencylayer.com/convert?access_key=" + Constantes.keys[3] + "&from={monedaOrigen}&to={monedasDestino}&amount={monto}", //-- "https://api.currencylayer.com/live?access_key=35bd65f691cd4da84c96a0d43bee050e&source={monedaOrigen}&currencies={monedasDestino}",
            "https://exchange-rates.abstractapi.com/v1/convert?api_key=" + Constantes.keys[4] + "&base={monedaOrigen}&target={monedasDestino}&base_amount={monto}", //-- "https://exchange-rates.abstractapi.com/v1/live/?api_key=14f87bf711cf4150be72596011d438d9&base={monedaOrigen}&target={monedasDestino}",
            "https://api.getgeoapi.com/v2/currency/convert?api_key=" + Constantes.keys[5] + "&format=json&from={monedaOrigen}&to={monedasDestino}&amount={monto}" //-- "https://api.getgeoapi.com/v2/currency/convert?api_key=7cedd25bb2afe334cac6ae53b02897b0958a72d5&from={monedaOrigen}&to={monedasDestino}&amount={monto}&format=json"
    };

    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;
    private Gson gson;

    private String[] monedasDestino = new String[Constantes.CI_LIMITE_ARRAY_MONEDAS];
    private Double[][] montoEquivalente = new Double[Constantes.CI_LIMITE_ARRAY_MONEDAS][Constantes.CI_LIMITE_ARRAY_EXCHANGE]; //[fila][columna] == [5][10]

    private ResultadoConversion resultadoConversion;


    public ClienteConversor(String monedaOrigen, boolean[] monedasDestino, int monto) {
        //Asigna los datos de la conversion
        this.resultadoConversion = new ResultadoConversion(monedaOrigen, monto);

        //nuestro cliente
        this.client = HttpClient.newHttpClient();

        //asignando las monedas detino seleccionadas

        asignaMonedasDestino(monedasDestino);
    }

    private void asignaMonedasDestino(boolean[] monedasDestino) {
        for (int i = 0; i < monedasDestino.length; i++) {
            this.monedasDestino[i] = null;
            if ( monedasDestino[i] ) {
                this.monedasDestino[i] = Constantes.listaMonedas[i];
            }
        }
    }

    public void procesaConversion() {
        //Procesa la conversion del monto de la monedaOrigen a las monedasDestino, en todos los exChange que usaremos.
        for (int i = 0; i < this.monedasDestino.length; i++) {
            if ( this.monedasDestino[i] != null ) {
                this.resultadoConversion.setIdMonedaDestino(i);
                for (int j = 0; j < this.urlsMoneyExchange.length; j++) {
                    this.resultadoConversion.setIdExchange(j);
                    String json = obtenInformcionExChange();
                    double montoConvertido = obtenConversiones(json);
                    this.resultadoConversion.setMontoConvertido(montoConvertido);
                    montoEquivalente[i][j] = montoConvertido;
                }
            }
        }
    }

    public void imprimeConversion() {
        // Encabezado de la tabla
        this.imprimeListaMoneyExchange();
        for (int i = 0; i < this.monedasDestino.length; i++) {
            if ( this.monedasDestino[i] != null ) {
                // Imprimir los datos de conversion de la moneda  cada exchange
                System.out.printf(" %s -->  ", Constantes.reportListaMonedas[i]);
                System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s%n", formateaValoresDeMonedaConvertido(i));  //montoEquivalente[i]
            }
        }
    }

    private void imprimeListaMoneyExchange() {
        // Encabezado de la tabla
        System.out.printf("%s", Constantes.CS_BLANCO.repeat(31));
         System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s%n", Constantes.listaMoneyExchange);
    }

    private String[] formateaValoresDeMonedaConvertido(int indiceMonedaConvertida) {
        String[] numeroFormateado = new String[Constantes.CI_LIMITE_ARRAY_EXCHANGE];

        for (int j = 0; j < montoEquivalente[indiceMonedaConvertida].length; j++) {
            double numero = montoEquivalente[indiceMonedaConvertida][j];
            if (numero != Constantes.CI_ERROR_SERVICE) {
                DecimalFormat df = new DecimalFormat(Constantes.CS_PATRON_MONEDA);
                numeroFormateado[j] = df.format(numero);
                //System.out.println("Número formateado: " + numeroFormateado);
            } else {
                numeroFormateado[j] = Constantes.CS_SIN_INFO;
            }
        }
        return numeroFormateado;
    }

    private String obtenInformcionExChange() {
        String uri = obtenUrl();

        //nuestro cliente
        //--this.client = HttpClient.newHttpClient();

        //lo que vamos a pedir
        //HttpRequest NO puede ser instanciado de forma directo pq es un metodo Abstracto, por eso e usa el PATRON builder
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();

        //lo que vamos a recibir
        try {
            this.response = this.client.send(this.request, HttpResponse.BodyHandlers.ofString());  //-- linea 1/2 de productivo
            String json = this.response.body();  //-- linea 2/2 de productivo
            //---String json = obtenJson();  //esta linea 1/1 es solo para PRUEBAS
            if ( json == null ) {
                throw new ErrorConsultaApiException("Error. No se pudo obtener información de : " + this.resultadoConversion);
            }

            //rmv:System.out.println("json: " + json);

            return json;

        } catch (IOException | InterruptedException | JsonSyntaxException e) {  //Las excepciones: IOException | InterruptedException, se deben PONER para PRODUCTIVO, y se deben QUITAR para PRUEBAS
            throw new ErrorConsultaApiException("Error.ClienteConversor." + e.getClass().getName() + ".  " + e.getMessage());
        }
    }

    private String obtenUrl() {
        if ( this.resultadoConversion.getIdExchange() >= 0 && this.resultadoConversion.getIdExchange() < (this.urlsMoneyExchange.length) ) {
            String url = this.urlsMoneyExchange[this.resultadoConversion.getIdExchange()]
                    .replace("{monedaOrigen}", this.resultadoConversion.getMonedaOrigen())
                    .replace("{monedasDestino}", this.resultadoConversion.getMonedaDestino())
                    .replace("{monto}", String.valueOf(this.resultadoConversion.getMonto()));
            //rmv: System.out.println("url = " + url);
            return url;
        }

        throw new ErrorConsultaApiException("Error. No se pudo obtener la url de estos datos : " + this.resultadoConversion);
    }

    private String obtenJson() {
        switch (this.resultadoConversion.getIdExchange()) {
            case 0:
                return "{\"result\":\"success\",\"documentation\":\"https://www.exchangerate-api.com/docs\",\"terms_of_use\":\"https://www.exchangerate-api.com/terms\",\"time_last_update_unix\":1747094402,\"time_last_update_utc\":\"Tue, 13 May 2025 00:00:02 +0000\",\"time_next_update_unix\":1747180802,\"time_next_update_utc\":\"Wed, 14 May 2025 00:00:02 +0000\",\"base_code\":\"EUR\",\"target_code\":\"MXN\",\"conversion_rate\":21.7657,\"conversion_result\":435.314}";
            case 1:
                return "{\"data\":{\"MXN\": 19.6382223551}}";
            case 2:
                return "{\"base\": \"USD\", \"amount\": \"11.5\", \"result\": { \"MXN\": 225.0412, \"rate\": 19.5688 }, \"ms\": 1}";
            case 3:
                return "{ \"success\": true, \"terms\": \"https://currencylayer.com/terms\", \"privacy\": \"https://currencylayer.com/privacy\", \"query\": { \"from\": \"USD\", \"to\": \"MXN\", \"amount\": 10}, \"info\": { \"timestamp\": 1430068515, \"quote\": 0.658443 }, \"result\": 6.58443 }";
            case 4:
                return "{ \"base\": \"USD\", \"target\": \"MXN\", \"base_amount\": 10, \"converted_amount\": 196.315141, \"exchange_rate\": 19.631514, \"last_updated\": 1746537300 }";
            case 5:
                return "{\"status\":\"success\", \"updated_date\":\"2018-12-27\", \"base_currency_code\":\"EUR\", \"amount\":10, \"base_currency_name\":\"Euro\", \"rates\":{ \"GBP\":{\"currency_name\":\"Pound Sterling\", \"rate\":\"0.9007\", \"rate_for_amount\":\"9.0070\" } } }";
            default:
                return null;
        }
    }

    private double obtenConversiones(String json) {
        MapeaConversion mapeo = new MapeaConversion();
        double ajusteMonto = 1.0;  //se usa para cuando la respuesta del exchange,solo trae cuanto cuenta la moneda convertida, pero no hace la conversion por el monto a convertir solicitado
        //-- {"exChangeRate", "freeCurrencyApi", "exConvert", "currencyLayer", "abstractApi", "currencyApi"}
        switch ( this.resultadoConversion.getIdExchange() ) {
            case 0:  //exChangeRate
                if ( json.contains("result") && json.contains("success") ) {
                    //manejo de json: serializar y desserializacion
                    // serializar
                    this.gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)  //los campos inician com letra en Mayusculas
                            .setPrettyPrinting()  //hace que el formato de json se vea ordenado y mas legible
                            .create();
                    ExChangeRateDto infoExChangeRate = this.gson.fromJson(json, ExChangeRateDto.class);
                    mapeo = new MapeaConversion(infoExChangeRate);
                }
                break;
            case 1:  //freeCurrencyApi
                if ( json.contains("data") && !json.contains("errors") ) {
                    //manejo de json: serializar y desserializacion
                    // serializar
                    this.gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)  //los campos inician com letra en Mayusculas
                            .setPrettyPrinting()  //hace que el formato de json se vea ordenado y mas legible
                            .create();
                    RespuestaFreeCurrencyApiDto infoFreeCurrencyApi = this.gson.fromJson(json, RespuestaFreeCurrencyApiDto.class);
                    mapeo = new MapeaConversion(infoFreeCurrencyApi);
                    ajusteMonto = this.resultadoConversion.getMonto();
                }
                break;
            case 2:  //exConvert
                if ( json.contains("base") && json.contains("result") && !json.contains("Invalid request") ) {
                    //manejo de json: serializar y deserializacion
                    // serializar
                    this.gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)  //los campos inician com letra en Mayusculas
                            .setPrettyPrinting()  //hace que el formato de json se vea ordenado y mas legible
                            .create();
                    RespuestaExConvertDto infoExConvert = this.gson.fromJson(json, RespuestaExConvertDto.class);
                    mapeo = new MapeaConversion(infoExConvert);
                }
                break;
            case 3:  //currencyLayer
                if ( json.contains("success") && json.contains("true") && json.contains("query")  && json.contains("result")  && !json.contains("error") ) {
                    //manejo de json: serializar y desserializacion
                    // serializar
                    this.gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)  //los campos inician com letra en Mayusculas
                            .setPrettyPrinting()  //hace que el formato de json se vea ordenado y mas legible
                            .create();
                    RespuestaCurrencyLayerDto infoCurrencyLayer = this.gson.fromJson(json, RespuestaCurrencyLayerDto.class);
                    mapeo = new MapeaConversion(infoCurrencyLayer);
                }
                break;
            case 4:  //abstractApi
                if ( json.contains("base") && json.contains("target") && json.contains("converted_amount") && !json.contains("error") ) {
                    //manejo de json: serializar y desserializacion
                    // serializar
                    this.gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)  //los campos inician com letra en Mayusculas
                            .setPrettyPrinting()  //hace que el formato de json se vea ordenado y mas legible
                            .create();
                    AbstractApiDto infoAbstractApi = this.gson.fromJson(json, AbstractApiDto.class);
                    mapeo = new MapeaConversion(infoAbstractApi);
                }
                break;
            case 5:  //currencyApi
                if ( json.contains("status") && json.contains("success") && json.contains("rates") && json.contains("rate_for_amount") && !json.contains("failed")  && !json.contains("error") ) {
                    //manejo de json: serializar y desserializacion
                    // serializar
                    this.gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)  //los campos inician com letra en Mayusculas
                            .setPrettyPrinting()  //hace que el formato de json se vea ordenado y mas legible
                            .create();
                    RespuestaCurrencyApiDto infoCurrencyApi = this.gson.fromJson(json, RespuestaCurrencyApiDto.class);  //no se tiene impleementado, para futuras versiones
                    mapeo = new MapeaConversion(infoCurrencyApi);
                }
                break;
            default:
                mapeo = new MapeaConversion("");
                System.out.println(String.format("Exchage no soportado, info enviada: %s ", this.urlsMoneyExchange[this.resultadoConversion.getIdExchange()], this.resultadoConversion));
                break;
        }
        if ( mapeo.getMontoConvertido() != Constantes.CI_ERROR_SERVICE ) {
            this.resultadoConversion.setMontoConvertido(mapeo.getMontoConvertido() * ajusteMonto);
            //rmv: System.out.println(String.format("Información obtenida del exchage  [ %s ], info: %s ", Constantes.listaMoneyExchange[this.resultadoConversion.getIdExchange()], this.resultadoConversion));
            //rmv:ojo: System.out.println("El resultado de la conversión de monedas es: " + mapeo.getMontoConvertido() + " el monto de ajuste es: " + ajusteMonto);

            return mapeo.getMontoConvertido() * ajusteMonto;
        }
        return mapeo.getMontoConvertido();  //retorna el valor de: Constantes.CI_ERROR_SERVICE
    }
}
