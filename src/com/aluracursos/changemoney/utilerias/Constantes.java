package com.aluracursos.changemoney.utilerias;

public class Constantes {
    public static String CS_IGUAL = "=";
    public static String CS_BLANCO = " ";
    public static String CS_VACIO = "";

    public static int CI_LIMITE = 200;  //170;
    public static int CI_LIMITE_ARRAY_MONEDAS = 11;
    public static int CI_LIMITE_ARRAY_EXCHANGE = 6;
    public static int CI_MENU_ORIGEN = 1;
    public static int CI_MENU_DESTINO = 2;
    public static int CI_MENU_CONVERSOR = 3;

    public static int CI_ERROR_SERVICE = -100;

    public static String CS_LINEA_CORTA_IZQ = "=====>";
    public static String CS_LINEA_CORTA_DER = "<=====";

    public static String green = "\033[32m";
    public static String blue = "\033[34m";
    public static String purple = "\033[35m";
    public static String red = "\033[31m";
    public static String negro = "\033[30m";
    public static String amarillo = "\033[33m";
    public static String cyan = "\033[36m";
    public static String blanco = "\033[37m";
    public static String reset = "\u001B[0m";

    public static String CS_PATRON_MONEDA = "###,###,###,###,###,##0.000000";
    public static String CS_SIN_INFO = "Sin-Información";

    public static String[] listaMonedas = {"MXN", "USD", "EUR", "JPY", "GBP", "RUB", "BRL", "CAD", "CYN", "CNY", "INR"};
    public static String[] helpListaMonedas = {"MXN($)", "USD($)", "EUR(€ Euro)", "JPY(¥ Yen)", "GBP(£ Libra E)", "RUB(Rublo)", "BRL(r$ RealBr)", "CAD(CA$ Canada)", "CYN(C¥N Yuan-Error)", "CYN(CN¥ Yuan)", "INR(Rs Rupia Indú)"};
    public static String[] reportListaMonedas = {"                MXN($)💵", "                USD($)💲", "           EUR(€ Euro)💶", "            JPY(¥ Yen)💴", "        GBP(£ Libra E)💷", "            RUB(Rublo)🪙", "        BRL(r$ RealBr)🏦", "       CAD(CA$ Canada)🍁", "   CYN(CN¥ Yuan-Error)🏮", "CYN(CN¥ Yuan Renminbi)🀄", "    INR(Rs Rupia Indú)🕌"};
    public static int[] espaciosEntreMontos = {6, 12, 12, 12, 12, 12};
    public static String[] listaMoneyExchange = {"exChangeRate", "freeCurrencyApi", "exConvert", "currencyLayer", "abstractApi", "currencyApi"};
    public static String[] iconosMonedas = {"MXN: 💵", // Peso Mexicano
            "USD: 💲", // Dólar Estadounidense
            "EUR: 💶", // Euro
            "JPY: 💴", // Yen Japonés
            "GBP: 💷", // Libra Esterlina
            "RUB: 🪙", // Rublo Ruso
            "BRL: 🏦", // Real Brasileño
            "CAD: 🍁", // Dólar Canadiense
            "CYN: 🏮", // Yuan Chino - error
            "CNY: 🀄", // Yuan Chino
            "INR: 🕌"  // Rupia India
    };

    public static String[] keys = {"6cd729a7324f5df6c253e846", "fca_live_kSgK2Y5QHukvrTszWLbgYyJqebMb1tqmEHAG0Goa", "4d8c7372-001c9c88-d96eb95d-83b4c6ab", "3f754643fbd207d57f9cb51b82f4cf4c", "e0912515a609497e9ced58eb9be3b6d2", "7cedd25bb2afe334cac6ae53b02897b0958a72d5"};

    public static String[] banner =
            {
                    "      ___                    ___                    ___           ___           ___           ___                 ",
                    "     /  /\\                  /__/\\                  /__/\\         /  /\\         /__/\\         /  /\\          ___   ",
                    "    /  /:/                  \\  \\:\\                |  |::\\       /  /::\\        \\  \\:\\       /  /:/_        /__/|  ",
                    "   /  /:/                    \\  \\:\\               |  |:|:\\     /  /:/\\:\\        \\  \\:\\     /  /:/ /\\      |  |:|  ",
                    "  /  /:/  ___            ___  \\  \\:\\            __|__|:|\\:\\   /  /:/  \\:\\   _____\\__\\:\\   /  /:/ /:/_     |  |:|  ",
                    " /__/:/  /  /\\          /__/\\  \\__\\:\\          /__/::::| \\:\\ /__/:/ \\__\\:\\ /__/::::::::\\ /__/:/ /:/ /\\  __|__|:|  ",
                    " \\  \\:\\ /  /:/          \\  \\:\\ /  /:/          \\  \\:\\~~\\__\\/ \\  \\:\\ /  /:/ \\  \\:\\~~\\~~\\/ \\  \\:\\/:/ /:/ /__/::::\\  ",
                    "  \\  \\:\\  /:/            \\  \\:\\  /:/            \\  \\:\\        \\  \\:\\  /:/   \\  \\:\\  ~~~   \\  \\::/ /:/     ~\\~~\\:\\ ",
                    "   \\  \\:\\/:/              \\  \\:\\/:/              \\  \\:\\        \\  \\:\\/:/     \\  \\:\\        \\  \\:\\/:/        \\  \\:\\",
                    "    \\  \\::/                \\  \\::/                \\  \\:\\        \\  \\::/       \\  \\:\\        \\  \\::/          \\__\\/",
                    "     \\__\\/                  \\__\\/                  \\__\\/         \\__\\/         \\__\\/         \\__\\/                "
            };



}
