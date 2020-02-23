package com.arceapps.feedreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetalleNoticia extends AppCompatActivity {

    ImageView imageView;
    TextView textViewTitulo;
    TextView textViewDescription;
    TextView textViewFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticia);


        //Recogemos los datos de la noticia con un bundle y los cargamos
        Bundle bundle = getIntent().getExtras();
        String imagen = bundle.getString("Imagen");
        String titulo= bundle.getString("Titulo");
        String description = bundle.getString("Description");
        String url = bundle.getString("Link");
        String fecha = bundle.getString("Fecha");

        imageView = findViewById(R.id.image_view_detalle_noticia);
        textViewTitulo = findViewById(R.id.tv_titulo_detalle_noticia);
        textViewDescription = findViewById(R.id.tv_description_detalle_noticia);
        textViewFecha = findViewById(R.id.tv_fecha_detalle_noticia);

        textViewTitulo.setText(titulo);
        textViewDescription.setText(description);
        textViewFecha.setText(fecha);

        cargarImagen(imagen);
        iniciarWebView(url);

    }

    private void iniciarWebView(String url){
        android.webkit.WebView webView = findViewById(R.id.web_view_detalle_noticia);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    //Cargamos la imagen de cada noticia con la librería Picasso
    private void cargarImagen(String noticia){
        if(!noticia.isEmpty() || noticia != null){
            ImageView ivBasicImage = findViewById(R.id.image_view_detalle_noticia);
            Picasso.get().load(noticia).into(ivBasicImage);
        }
    }
}
