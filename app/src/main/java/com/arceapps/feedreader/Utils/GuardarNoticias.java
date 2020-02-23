package com.arceapps.feedreader.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.arceapps.feedreader.Noticia;

import java.util.ArrayList;

/**
 * Created by: Oscar Arce on 23/02/2020.
 * Company: Arce Apps.
 * E-mail: arce.com.apps@gmail.com
 * Copyright (c) 2020 All rights reserved.
 */
public class GuardarNoticias {

    public static void saveValuePreference(Context context, ArrayList<Noticia> noticia) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        int num = 0;
        for (Noticia i : noticia) {
            editor.putString("NoticiaTitulo" + num, i.getmTitulo());
            editor.putString("NoticiaLink" + num, i.getmLink());
            editor.putString("NoticiaFoto" + num, i.getmImagen());
            editor.putString("NoticiaDescription" + num, i.getmDescripcion());
            num++;
            editor.putInt("NoticiaNumero", num);
        }
        editor.commit();
    }

    public static ArrayList<Noticia> getValuePreference(Context context, ArrayList<Noticia> noticias) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        ArrayList<Noticia> arrayList = new ArrayList<>();
        for (int i = 0; i < preferences.getInt("NoticiaNumero", 0); i++) {
            preferences.getString("NoticiaTitulo" + i, "");
            preferences.getString("NoticiasLink" + i, "");
            preferences.getString("NoticiaFoto" + i, "");
            preferences.getString("NoticiaDescription" + i, "");
        }
        return arrayList;
    }
}
