package com.aluracursos.changemoney.dto.currencylayer;

public record RespuestaCurrencyLayerDto(boolean success, double result, String terms, QueryCurrencyLayerDto data, InfoCurrencyLayerDto info ) {}
