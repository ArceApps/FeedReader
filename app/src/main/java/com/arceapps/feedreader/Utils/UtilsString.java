package com.arceapps.feedreader.Utils;

/**
 * Created by: Oscar Arce on 23/02/2020.
 * Company: Arce Apps.
 * E-mail: arce.com.apps@gmail.com
 * Copyright (c) 2020 All rights reserved.
 */
public class UtilsString {

    //Método que usamos para quitar las partes que no queremos usar del texto de la descripción de la noticia.
    public static String separarDescription(String string) {
        String descripcionOriginal = string;
        String separador = "\\/>";
        String[] separarPartes = descripcionOriginal.split(separador);
        String devolverString = separarPartes[1];

        return quitarEspaciosEnBlanco(devolverString);
    }

    //Eliminar posibles espacios en blanco al inicio y final de los String
    public static String quitarEspaciosEnBlanco(String string){
        String stringSinEspacios = string.trim();
        return stringSinEspacios;
    }
}
