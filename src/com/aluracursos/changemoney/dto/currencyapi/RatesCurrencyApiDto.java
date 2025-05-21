package com.aluracursos.changemoney.dto.currencyapi;

import com.aluracursos.changemoney.dto.currencyapi.monedas.*;

public record RatesCurrencyApiDto(MonedaPesosMexicanos MXN, MonedaDolarAmericano USD , MonedaEuro EUR,
                                  MonedaYenJapones JPY, MonedaLibraEsterlina GBP, MonedaRubloRuso RUB,
                                  MonedaRealBrasilenio BRL, MonedaDolarCanadiense CAD,
                                  MonedaYuanChinoError CYN, MonedaYuanChino CNY, MonedaRupiaIndu INR) {
}
