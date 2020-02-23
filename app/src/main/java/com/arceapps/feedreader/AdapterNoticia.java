package com.arceapps.feedreader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arceapps.feedreader.Utils.UtilsFecha;
import com.arceapps.feedreader.Utils.UtilsString;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by: Oscar Arce on 21/02/2020.
 * Company: Arce Apps.
 * E-mail: arce.com.apps@gmail.com
 * Copyright (c) 2020 All rights reserved.
 */
public class AdapterNoticia extends RecyclerView.Adapter<AdapterNoticia.MyViewHolder> {

    ArrayList<Noticia> noticias;
    Context context;

    public AdapterNoticia(ArrayList<Noticia> noticias, Context context) {
        this.noticias = noticias;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(context).inflate(R.layout.item_noticia, parent, false);
        MyViewHolder holder = new MyViewHolder(viewItem);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Noticia noticiaLoad = noticias.get(position);

        holder.mViewTituloNoticia.setText(UtilsString.quitarEspaciosEnBlanco(noticiaLoad.getmTitulo()));
        holder.mViewDescriptionNoticia.setText(UtilsString.separarDescription(noticiaLoad.getmDescripcion()));
        holder.mViewFechaNoticia.setText(UtilsFecha.formatearFecha(noticiaLoad.getmFecha()));
        holder.mImageViewImagenNoticia.setImageResource(R.drawable.pic_400x200);

        cargarImagen(noticiaLoad, holder);
        String imagenNoticia = noticiaLoad.getmImagen();
        String tituloNoticia = UtilsString.quitarEspaciosEnBlanco(noticiaLoad.getmTitulo());
        String descriptionNoticia = UtilsString.separarDescription(noticiaLoad.getmDescripcion());
        String fechaNoticia = UtilsFecha.formatearFecha(noticiaLoad.getmFecha());
        String urlNoticia = noticiaLoad.getmLink();
        enlaceNoticiaDetalleNoticia(imagenNoticia, tituloNoticia, descriptionNoticia, fechaNoticia, urlNoticia, holder.mImageViewImagenNoticia);

    }

    //Cargamos la imagen de cada noticia con la librería Picasso
    private void cargarImagen(Noticia noticiaLoad, MyViewHolder holder){
        if(noticiaLoad.getmImagen().isEmpty() || noticiaLoad.getmImagen() == null){
            noticiaLoad.setmImagen(String.valueOf(R.drawable.pic_400x200));
        }else {
            String imageUri = noticiaLoad.getmImagen();
            ImageView ivBasicImage = holder.mImageViewImagenNoticia;
            Picasso.get().load(imageUri).into(ivBasicImage);
        }
    }


    public void enlaceNoticiaDetalleNoticia(final String imagen, final String titulo, final String description, final String fecha, final String url, View vista){
        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetalleNoticia.class);
                intent.putExtra("Imagen", imagen);
                intent.putExtra("Titulo", titulo);
                intent.putExtra("Description", description);
                intent.putExtra("Fecha", fecha);
                intent.putExtra("Link", url);
                Log.d("enlace", titulo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noticias.size();
    }

    public void filtrarResultados(ArrayList<Noticia> noticiasFilter){
        noticias = new ArrayList<>();
        noticias.addAll(noticiasFilter);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mViewTituloNoticia;
        TextView mViewDescriptionNoticia;
        TextView mViewFechaNoticia;
        ImageView mImageViewImagenNoticia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mViewTituloNoticia = itemView.findViewById(R.id.tv_titulo);
            mViewDescriptionNoticia = itemView.findViewById(R.id.tv_description);
            mViewFechaNoticia = itemView.findViewById(R.id.tv_fecha);
            mImageViewImagenNoticia = itemView.findViewById(R.id.imageView);
        }
    }
}
