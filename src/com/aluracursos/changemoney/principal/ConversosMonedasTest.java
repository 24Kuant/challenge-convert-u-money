package com.aluracursos.changemoney.principal;

public class ConversosMonedasTest {
    public static void main(String[] args) {
        // Encabezado de la tabla
        System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s%n",
                "exChangeRate", "freeCurrencyApi", "exConvert",
                "currencyLayer", "abstractApi", "currencyApi");

        // Datos de ejemplo
        String[][] datos = {
                {"MXN($)", "87,577.200000", "87,499.000096", "86,963.850000", "86,963.670000", "Sin-Información", "86,850.000000"},
                {"EUR(€ Euro)", "4,025.250000", "0.000000", "4,002.300000", "4,002.304500", "Sin-Información", "4,002.331500"},
                {"JPY(¥ Yen)", "653,858.550000", "0.000000", "652,027.500000", "652,040.928000", "Sin-Información", "651,735.000000"},
                {"GBP(£ Libra E)", "3,385.800000", "0.000000", "3,368.025000", "Sin-Información", "Sin-Información", "3,367.998000"},
                {"RUB(Rublo)", "364,167.900000", "0.000000", "363,381.300000", "363,381.408000", "Sin-Información", "363,181.383000"},
                {"BRL(r$ RealBr)", "25,582.050000", "0.000000", "25,399.800000", "25,399.786500", "Sin-Información", "25,409.155500"},
                {"CAD(CA$ Canada)", "6,279.750000", "0.000000", "6,276.195000", "6,276.195000", "Sin-Información", "6,255.000000"},
                {"CYN(CN¥ Yuan)", "Sin-Información", "Sin-Información", "Sin-Información", "Sin-Información", "Sin-Información", "Sin-Información"},
                {"INR(Rs Rupia Indú)", "385,371.000000", "0.000000", "384,110.100000", "384,110.086500", "Sin-Información", "384,157.255500"}
        };

        // Imprimir los datos
        for (String[] fila : datos) {
            System.out.printf("%-25s %-25s %-25s %-25s %-25s %-25s%n", fila);
        }

        System.out.println("\n\n\n");

        String emoji = "\uD83D\uDE00"; // Emoji de una cara sonriente
        System.out.println("¡Hola! " + emoji);
    }
}
