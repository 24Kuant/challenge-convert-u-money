package com.aluracursos.changemoney.dto.exchangerate;

public record ExChangeRateDto(String result, String base_code, String target_code, double conversion_rate, double conversion_result) {
}
