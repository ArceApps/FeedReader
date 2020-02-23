package com.arceapps.feedreader.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by: Oscar Arce on 23/02/2020.
 * Company: Arce Apps.
 * E-mail: arce.com.apps@gmail.com
 * Copyright (c) 2020 All rights reserved.
 */
public class UtilsFecha {

    public static String formatearFecha(String dateFecha){
        String nuevaFecha;
        SimpleDateFormat formateoFecha = new SimpleDateFormat("E, d MMM yyyy");
        try {
            Date fecha = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss").parse(dateFecha);
            nuevaFecha = formateoFecha.format(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            nuevaFecha = dateFecha;
        }

        return nuevaFecha;
    }

    public static String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country;
    }
}
