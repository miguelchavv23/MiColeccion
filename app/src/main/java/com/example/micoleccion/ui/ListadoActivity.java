package com.example.micoleccion.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.micoleccion.R;
import com.example.micoleccion.adaptadores.ElementoAdapter;
import com.example.micoleccion.modelo.Elemento;

import java.util.ArrayList;
import java.util.List;

public class ListadoActivity extends AppCompatActivity {

    private RecyclerView rvColeccion;
    private TextView tvVacio;
    private ElementoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        rvColeccion = findViewById(R.id.rvColeccion);
        tvVacio = findViewById(R.id.tvVacio);

        List<Elemento> lista = datosDePrueba();

        adapter = new ElementoAdapter(lista);
        rvColeccion.setLayoutManager(new LinearLayoutManager(this));
        rvColeccion.setAdapter(adapter);

        mostrarVacioSiHaceFalta(lista);
    }

    private void mostrarVacioSiHaceFalta(List<Elemento> lista) {
        if (lista.isEmpty()) {
            tvVacio.setVisibility(View.VISIBLE);
            rvColeccion.setVisibility(View.GONE);
        } else {
            tvVacio.setVisibility(View.GONE);
            rvColeccion.setVisibility(View.VISIBLE);
        }
    }

    // Datos temporales: se borrarán cuando conectemos SQLite
    private List<Elemento> datosDePrueba() {
        List<Elemento> lista = new ArrayList<>();
        lista.add(new Elemento(1, "Zelda", "Aventura", "Completado", 1));
        lista.add(new Elemento(2, "Call of Duty", "Guerra", "Pendiente", 0));
        lista.add(new Elemento(3, "FIFA", "Deportes", "Pendiente", 1));
        return lista;
    }
}