package com.aluracursos.changemoney.dto.abstractapi;

public record AbstractApiDto(String base, String target, double base_amount, double converted_amount, double exchange_rate) {
}
