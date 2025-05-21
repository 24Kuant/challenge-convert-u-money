package com.aluracursos.changemoney.utilerias;

import com.aluracursos.changemoney.clienteapi.ClienteConversor;

import static java.util.Arrays.*;

public class Menus {

    private boolean[] opcionesMonedaOrigen   = new boolean[Constantes.CI_LIMITE_ARRAY_MONEDAS];
    private boolean[] opcionesMonedasDestino = new boolean[Constantes.CI_LIMITE_ARRAY_MONEDAS];
    Teclado teclado = new Teclado();


    public String mensajeListaMonedas(String[] lista, boolean esMonedaOrigen) {
        String cadena = Constantes.CS_VACIO;
        for (int i = 0; i < lista.length; i++) {
            String color = ( esMonedaOrigen ? (opcionesMonedaOrigen[i] ? Constantes.purple : Constantes.reset) : (opcionesMonedaOrigen[i] ? Constantes.red :  (opcionesMonedasDestino[i] ? Constantes.purple : Constantes.reset)) );
            cadena = cadena.concat(String.format("%s%d.%s%s ", color, (i + 1), Constantes.reset, lista[i]));
        }
        return cadena;
    }

    public void mensajeCorto(String mensaje, boolean centrar, boolean incluyeLineaDerecha, boolean saltoLinea) {
        boolean conLineaExtra = true;  //indica que por default si se incluye la linea extra.  =====>   ....     <=====
        mensajeCorto(mensaje, centrar, incluyeLineaDerecha, saltoLinea, conLineaExtra);
    }

    public void mensajeCorto(String mensaje, boolean centrar, boolean incluyeLineaDerecha, boolean saltoLinea, boolean conLineaExtra) {
        ubicarMensaje(Constantes.CS_VACIO, centrar, true, true, true);
        if (centrar) {
            ubicarMensaje(mensaje, centrar, incluyeLineaDerecha, true, saltoLinea);
        } else {
            System.out.println(Constantes.CS_LINEA_CORTA_IZQ + mensaje);
        }
        if ( conLineaExtra ) {
            ubicarMensaje(Constantes.CS_VACIO, centrar, true, true, true);
        }
    }

    public String ubicarMensaje(String mensaje, boolean centrar, boolean incluyeLineaDerecha, boolean imprimir, boolean saltoLinea) {
        String mensajeFinal;

        int blancos = Constantes.CI_LIMITE - (Constantes.CS_LINEA_CORTA_IZQ + QuitaFormatosEspeciales(mensaje) + (incluyeLineaDerecha ? Constantes.CS_LINEA_CORTA_DER : Constantes.CS_VACIO)).length();
        int mitadIzquierda = 0;
        int mitadDerecha = 0;

        if (centrar) {
            mitadIzquierda = (int) (blancos / 2.0);
            //System.out.println("blancos: " + blancos + ", mitadIzquierda: " + mitadIzquierda + "incluyeLineaDerecha: " + incluyeLineaDerecha);
        }

        mitadDerecha = blancos - mitadIzquierda;
        mensajeFinal = Constantes.CS_LINEA_CORTA_IZQ + Constantes.CS_BLANCO.repeat(mitadIzquierda) + mensaje + Constantes.CS_BLANCO.repeat(mitadDerecha) + (incluyeLineaDerecha ? Constantes.CS_LINEA_CORTA_DER : Constantes.CS_VACIO);

        if (imprimir) {
            System.out.print(mensajeFinal + (saltoLinea ? "\n" : Constantes.CS_VACIO));
        }

        return mensajeFinal;
    }

    private String QuitaFormatosEspeciales(String mensaje) {
        if( mensaje.equals(Constantes.CS_VACIO) || mensaje.equals(Constantes.CS_BLANCO)) {
            return mensaje;
        }

        return mensaje.replace(Constantes.green, "")
                .replace(Constantes.green, "")
                .replace(Constantes.blue, "")
                .replace(Constantes.purple, "")
                .replace(Constantes.red, "")
                .replace(Constantes.reset, "");
    }

    public void menuPrincipal() {
        int opcion = -1;

        this.imprimeBanner();
        while ( opcion != 99 ) {
            opcion = menuMonedaOrigen();
            opcion = menuMonedasDestino();
            if (opcion !=0 ) {
                opcion = menuMonedasConversor();
            }
        }
    }

    private int menuMonedaActual(int origenMenu) {
        int opcion = 0;

        menuMoneda(origenMenu);
        if ( origenMenu == Constantes.CI_MENU_ORIGEN || origenMenu == Constantes.CI_MENU_DESTINO) {
            boolean esMonedaOrigen = ( origenMenu == Constantes.CI_MENU_ORIGEN );
            opcion = opcionMenu(esMonedaOrigen);
        } else {  //-- CI_MENU_CONVERSOR
            opcion = opcionMenu();
        }

        if (opcion == 99 || opcion == -99) {
            mensajeCorto(String.format("%sGracias por usar: %sChanging your money - C-U-Money%s", Constantes.blue, Constantes.red, Constantes.reset), true, true, true);
            System.exit(0); // Termina el programa sin errores
        }
        return opcion;
    }

    private int menuMonedaOrigen() {
        return menuMonedaActual(Constantes.CI_MENU_ORIGEN);
    }

    private int menuMonedasDestino() {
        int opcion = -1;

        while (opcion != 0 && opcion != 50) {
            opcion = menuMonedaActual(Constantes.CI_MENU_DESTINO);

            if (opcion != 0 && todasLasOpcionesSeleccionadas(this.opcionesMonedasDestino)) {
                opcion = 50;
            }
        }
        return opcion;
    }

    private int menuMonedasConversor() {
        int opcion = -1;

        while ( opcion != 0 ) {
            opcion = menuMonedaActual(Constantes.CI_MENU_CONVERSOR);
            if ( opcion == -30 ) {
                menuMonedasDestino();
            }
        }
        return opcion;
    }

    private void menuMoneda(int origenMenu) {
        String titulo;
        String salida;

        salida = "99.Salir";
        titulo = "Tu dinero en: ";
        menuMonedaOrigen(true, salida, titulo);

        if ( origenMenu >= Constantes.CI_MENU_DESTINO ) {
            salida = "0.Regresar Menú de Moneda Orígen          50.Convertir Monedas          99.Salir";
            titulo = "Lo quieres en: ";
            menuMoneda(false, Constantes.CI_MENU_DESTINO, salida, titulo);
        }

        if ( origenMenu >= Constantes.CI_MENU_CONVERSOR ) {
            salida = "0.Regresar Menú de Moneda Origen          -30.Regresar Menú de Moneda Destino           -99.Salir";
            titulo = String.format("  %s¿Cuánto dinero quiere cambiar?  %s  ", Constantes.blue, Constantes.reset);
            menuMoneda(false, Constantes.CI_MENU_CONVERSOR, salida, titulo);
        }
    }

    private void menuMonedaOrigen(boolean esMonedaOrigen, String salida, String titulo) {
        System.out.print("\n");
        System.out.println(Constantes.CS_IGUAL.repeat(Constantes.CI_LIMITE));
        mensajeCorto("La mejor experiencia: Changing your money - C-U-Money", true, true, true);
        menuMoneda(esMonedaOrigen, Constantes.CI_MENU_ORIGEN, salida, titulo);
    }

    private void menuMoneda(boolean esMonedaOrigen, int origenMenu, String salida, String titulo) {

        ubicarMensaje(Constantes.CS_BLANCO, true, true, true, true);
        ubicarMensaje("<===== ======= ============   ========= ====== ======= =====>", true, true, true, true);
        ubicarMensaje(salida, true, true, true, true);
        String mensaje = "";
        boolean centrar = false;
        if ( origenMenu != Constantes.CI_MENU_CONVERSOR) {
            mensaje = mensajeListaMonedas(Constantes.helpListaMonedas, esMonedaOrigen);
            centrar = true;
        }
        ubicarMensaje(titulo + mensaje, centrar, true, true, true);
    }

    private int opcionMenu(boolean esMonedaOrigen) {
        int opcion = -1;
        //-int opcionMenor = ( esMonedaOrigen ? 1 : 0 );
        String mensaje = ( esMonedaOrigen ? "Origen" : "Destino" );

        boolean banderaSalida = true;

        //esMonedaOrigen = true: Obtiene la moneda Origen - solo permite una sola moneda.
        //esMonedaOrigen = false: Obtiene las monedas destino, pueden ser mas de una, MENOS la moneda Origen

        while ( banderaSalida ) {
            try {
                opcion = Integer.valueOf(teclado.obtenTeclado().next());  //se cambia de nextInt() a next() para que lo capturado pueda validar si es numero.con nextInt si pone letras falla y termina el programa.
                if (opcion >= 1 && opcion <= Constantes.CI_LIMITE_ARRAY_MONEDAS) {
                    if ( esMonedaOrigen ) {
                        fill(opcionesMonedaOrigen, false); // Inicializa todas las opciones en false usando una función lambda, porque solo se permite una opcion de la moneda origen.
                        opcionesMonedaOrigen[opcion - 1] = true;
                    } else {  //esMonedaDestino
                        if (opcionesMonedaOrigen[opcion - 1] == false) { //valida si la monedaOrigen es la misma ala moneda Destino
                            opcionesMonedasDestino[opcion - 1] = !opcionesMonedasDestino[opcion - 1];
                        } else {
                            mensajeCorto(String.format("%sError.%s La Moneda Origen y Destino %ses la misma!%s",Constantes.red, Constantes.reset, Constantes.cyan, Constantes.reset), false, false, false);
                            pausar();
                        }
                    }
                    banderaSalida = false;
                } else if ( opcion == 99 ) {
                    banderaSalida = false;
                } else if ( opcion == 50 ) {
                    if ( ningunaOpcionSeleccionada(opcionesMonedasDestino) ) {
                        mensajeCorto(String.format("Selecciona %sal Menos %suna moneda Destino!%s",Constantes.purple, Constantes.amarillo, Constantes.reset), false, false, false);
                        pausar();
                    } else {
                        banderaSalida = false;
                    }
                }
                else if ( !esMonedaOrigen && (opcion == 0) ) {
                    fill(opcionesMonedasDestino, false); // Inicializa todas las opciones en false usando una función lambda, para las opciones de las Monedas Destinos, ya que se presiono la opcion de salir de la captura de monedas destino.
                    banderaSalida = false;
                }
            } catch (NumberFormatException e) {
                mensajeCorto("Error.Moneda " + mensaje + ". Opción No valida. Intentelo nuevamente!", false, false, false);
                opcion = -1;
                //throw new NumberFormatException("Error. Opción No valida. Intentelo nuevamente");
            }
        }

        return opcion;
    }

    private int opcionMenu() {
        int opcion = -1;

        while ( opcion != 0 && opcion != -30 && opcion != -99 ) {
            try {
                opcion = Integer.valueOf(teclado.obtenTeclado().next());  //se cambia de nextInt() a next() para que lo capturado pueda validar si es numero.con nextInt si pone letras falla y termina el programa.
                if ( opcion == 0 ) {
                    fill(opcionesMonedasDestino, false); // Inicializa todas las opciones en false usando una función lambda, porque regresa al Menu de la Moneda Origen y se tiene que limpiar las opciones del Menu de Moneda Destino.
                }
                else if ( opcion != -30 && opcion != -99 ) {
                    //--System.out.println("monto tecleado : " + opcion);
                    System.out.println(String.format("\n%sFavor de esperar unos momentos, mientras se realiza la conversión de monedas en línea%s...\n",Constantes.green, Constantes.reset));
                    this.obtenConvesionMoneda(opcion);  //le manda el monto a convertir.
                    this.reiniciaMenu();  //Reinicia al Menu principal
                    opcion = 0;  //Se regresa al menu principal2
                }
            } catch (NumberFormatException e) {
                mensajeCorto("Error. Monto u Opción No valida. Intentelo nuevamente!", false, false, false);
                opcion = -1;
                //throw new NumberFormatException("Error. Opción No valida. Intentelo nuevamente");
            }
        }

        return opcion;
    }
    private boolean todasLasOpcionesSeleccionadas(boolean[] listaOpcionesMoneda) {
        int total = totalElementosSeleccionados(listaOpcionesMoneda);

        return ( total == (Constantes.CI_LIMITE_ARRAY_MONEDAS-1) );
    }

    private void reiniciaMenu() {
        //Presiona cualquier tecla y lueo ENTER para continuar..
        System.out.println();
        mensajeCorto(String.format("%s%s%s", Constantes.purple, "Presiona cualquier tecla y luego [ENTER] para continuar!", Constantes.reset), false, false, false);
        teclado.obtenTeclado().next();
        fill(opcionesMonedasDestino, false); // Inicializa todas las opciones en false usando una función lambda, porque regresa al Menu de la Moneda Origen y se tiene que limpiar las opciones del Menu de Moneda Destino.
    }

    private boolean ningunaOpcionSeleccionada(boolean[] listaOpcionesMoneda) {
        int total = totalElementosSeleccionados(listaOpcionesMoneda);

        return ( total == 0 );
    }

    private int totalElementosSeleccionados(boolean[] listaOpcionesMoneda) {
        /* no funciona para arreglos de boolean primitivo
        int contadorTrue = Arrays.stream(listaOpcionesMoneda)
                                .filter(valor -> true) // Filtra los valores true
                                .count(); // Cuenta los valores filtrados
        */

        int contadorTrue = 0;

        for (boolean valor : listaOpcionesMoneda) {
            if (valor) {
                contadorTrue++;
            }
        }

        return contadorTrue;
    }

    private void pausar() {
        try {
            Thread.sleep(3000); // Pausa por 3 segundos
        } catch (InterruptedException e) {
            System.out.println("Error en timer sleep. " + e.getMessage());
        }
    }

    private void obtenConvesionMoneda(int monto) {
        //--System.out.println("Monto a convertir: " + monto);
        // Encabezado de la tabla
        //--System.out.println( Constantes.CS_BLANCO.repeat(25) + mensajeListaMoneyExchange(Constantes.listaMoneyExchange) );
        String monedaOrigen = obtenMonedaOrigen();
        int totalMonedasDestino = asignaMonedasDestino();  //asigna ls monedas destino seleccionadas y las pone en el arreglo: monedasDestino

        if ( monedaOrigen == null || totalMonedasDestino == 0) {
            System.out.println("EEEEERRRROOOORRRRRR !!");
            return;
        }

        ClienteConversor cliente = new ClienteConversor(monedaOrigen, opcionesMonedasDestino, monto);
        cliente.procesaConversion();
        cliente.imprimeConversion();

        //System.out.println("==> Change " + Constantes.helpListaMonedas[indiceMonedaOrigen] + " to " + Constantes.helpListaMonedas[indiceMonedaDestino] + conversion);
    }

    private String obtenMonedaOrigen() {
        for (int i = 0; i < opcionesMonedaOrigen.length; i++) {
            if ( opcionesMonedaOrigen[i] ) {
                return Constantes.listaMonedas[i];
            }
        }

        return null;
    }

    private int asignaMonedasDestino() {
        /* isando for i
        int total = 0;
        for (int i = 0; i < opcionesMonedasDestino.length; i++) {
            if ( opcionesMonedasDestino[i] ) {
                total++;
            }
        }
        return total;
        */

        /* usando for */
        int total = 0;
        for (boolean opcion : opcionesMonedasDestino) {
            if (opcion) {
                total++;
            }
        }
        return total;

        /*usando funciones lamda
        return (int) Arrays.stream(opcionesMonedasDestino)
                        .filter(opcion -> opcion)
                        .count();
         */
    }

    private void imprimeBanner() {
        ubicarMensaje(Constantes.CS_VACIO, true, true, true, true);
        for (String linea : Constantes.banner) {
            //mensajeCorto(String.format("%s%s%s", Constantes.purple, linea, Constantes.reset), true, true, true, false);
            //System.out.println(linea);
            ubicarMensaje(String.format("%s%s%s", Constantes.purple, linea, Constantes.reset), true, true, true, true);
        }
        ubicarMensaje(Constantes.CS_VACIO, true, true, true, false);
    }
}
