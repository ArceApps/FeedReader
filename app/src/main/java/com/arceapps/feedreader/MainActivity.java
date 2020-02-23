package com.arceapps.feedreader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    RecyclerView recyclerView;
    LeerFeed leerFeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Comprobamos si tenemos conexión a internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            recyclerView = findViewById(R.id.recycled_view);
            leerFeed = new LeerFeed(this, recyclerView);
            leerFeed.execute();
            Snackbar.make(recyclerView, R.string.conexion_internet_ok, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            //TODO Poner aquí las noticias que hemos guardado anteriormente si las hay
            Snackbar.make(recyclerView, R.string.conexion_internet_error, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Comprobamos si tenemos conexión a internet y recargamos las noticias
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    recyclerView = findViewById(R.id.recycled_view);
                    leerFeed.recargarNoticias();
                    Snackbar.make(recyclerView, R.string.recarga_noticias, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(recyclerView, R.string.conexion_internet_error, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String textoBusqueda = newText.toLowerCase();
        AdapterNoticia adapterNoticia = new AdapterNoticia(leerFeed.enviarNoticiasParaFiltrar(), this);
        ArrayList<Noticia> nota = new ArrayList<>();

        for(Noticia noticia : leerFeed.enviarNoticiasParaFiltrar()){
            if(noticia.getmTitulo().toLowerCase().contains(textoBusqueda)){
                nota.add(noticia);
            }
        }

        adapterNoticia.filtrarResultados(nota);
        leerFeed.loadAdaptador(nota, this);

        return true;
    }
}
