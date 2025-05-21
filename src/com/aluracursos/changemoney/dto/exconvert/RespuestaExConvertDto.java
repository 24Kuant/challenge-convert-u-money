package com.aluracursos.changemoney.dto.exconvert;

import com.aluracursos.changemoney.dto.freecurrencyapi.DataFreeCurrencyApiDto;

public record RespuestaExConvertDto(String base, double amount, ResultExConvertDto result) {}
