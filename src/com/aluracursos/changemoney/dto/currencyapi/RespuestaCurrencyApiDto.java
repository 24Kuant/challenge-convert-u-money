package com.aluracursos.changemoney.dto.currencyapi;

import com.aluracursos.changemoney.dto.currencylayer.InfoCurrencyLayerDto;
import com.aluracursos.changemoney.dto.currencylayer.QueryCurrencyLayerDto;

public record RespuestaCurrencyApiDto(double amount, String status, String updated_date, String base_currency_code, String base_currency_name, RatesCurrencyApiDto rates ) {}
