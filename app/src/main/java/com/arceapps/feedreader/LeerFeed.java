package com.arceapps.feedreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arceapps.feedreader.Utils.GuardarNoticias;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by: Oscar Arce on 20/02/2020.
 * Company: Arce Apps.
 * E-mail: arce.com.apps@gmail.com
 * Copyright (c) 2020 All rights reserved.
 */
public class LeerFeed extends AsyncTask<Void, Void, Void> {


    private Context context;
    private ProgressDialog mProgressDialog;
    private ArrayList<Noticia> noticias;
    private RecyclerView recyclerView;

    //Direccion url desde donde se obtienen los datos
    String mDireccion = "https://actualidad.rt.com/feeds/all.rss";
    private URL mUrl;

    LeerFeed(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("Cargando");
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mProgressDialog.dismiss();
        loadAdaptador(noticias, context);
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        procesarRss(obtenerDatos());
        return null;
    }

    public void recargarNoticias(){
        procesarRss(obtenerDatos());
        loadAdaptador(noticias, context);
    }

    public void loadAdaptador(ArrayList<Noticia> noticiaArrayList, Context contexto){
        //Cargamos el adaptador con las noticias
        AdapterNoticia adapterNoticia = new AdapterNoticia(noticiaArrayList, contexto);
        recyclerView.setLayoutManager(new LinearLayoutManager(contexto));
        recyclerView.setAdapter(adapterNoticia);
    }


    private void procesarRss(Document data){
        if(data != null){
            //Almacenamos las noticias
            noticias = new ArrayList<>();

            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList nodeList = channel.getChildNodes();

            //Recorremos los item de la página de noticias
            for (int i = 0; i < nodeList.getLength(); i++){
                Node etiquetaItemFicheroXml = nodeList.item(i);
                if(etiquetaItemFicheroXml.getNodeName().equalsIgnoreCase("item")){

                    Noticia noticia = new Noticia();
                    NodeList itemHijo = etiquetaItemFicheroXml.getChildNodes();

                    //Recorremos los elementos de cada itemHijo
                    for(int j = 0; j < itemHijo.getLength(); j++){
                        Node nodeActual = itemHijo.item(j);
                        //Log.d("Elementos del rss", nodeActual.getTextContent());

                        if(nodeActual.getNodeName().equalsIgnoreCase("title")){
                            noticia.setmTitulo(nodeActual.getTextContent());
                            continue;
                        }else if (nodeActual.getNodeName().equalsIgnoreCase("link")){
                            noticia.setmLink(nodeActual.getTextContent());
                            continue;
                        }else if (nodeActual.getNodeName().equalsIgnoreCase("description")){
                            noticia.setmDescripcion(nodeActual.getTextContent());
                            continue;
                        }else if (nodeActual.getNodeName().equalsIgnoreCase("pubdate")){
                            noticia.setmFecha(nodeActual.getTextContent());
                            continue;
                        }else if (nodeActual.getNodeName().equalsIgnoreCase("enclosure")){
                            String url = nodeActual.getAttributes().item(0).getTextContent();
                            noticia.setmImagen(url);
                            continue;
                        }
                    }
                    noticias.add(noticia);
                    GuardarNoticias.saveValuePreference(context, noticias);
                }
            }
        }
    }

    public ArrayList<Noticia> enviarNoticiasParaFiltrar(){
        return noticias;
    }

    //Obtener los datos del rss de las noticias
    private Document obtenerDatos(){
        try {
            mUrl = new URL(mDireccion);

            //Creamos conexión http
            HttpURLConnection urlConnection = (HttpURLConnection)mUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document rssDocument = documentBuilder.parse(inputStream);

            return rssDocument;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
