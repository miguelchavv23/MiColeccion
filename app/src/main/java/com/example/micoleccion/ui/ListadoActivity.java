package com.example.micoleccion.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.micoleccion.R;
import com.example.micoleccion.adaptadores.ElementoAdapter;
import com.example.micoleccion.datos.BaseDatos;
import com.example.micoleccion.modelo.Elemento;

import java.util.ArrayList;
import java.util.List;

public class ListadoActivity extends AppCompatActivity {

    private RecyclerView rvColeccion;
    private TextView tvVacio;
    private Button btnNuevo;
    private Button btnAjustes;
    private ElementoAdapter adapter;
    private BaseDatos bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        rvColeccion = findViewById(R.id.rvColeccion);
        tvVacio = findViewById(R.id.tvVacio);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnAjustes = findViewById(R.id.btnAjustes);

        bd = new BaseDatos(this);

        adapter = new ElementoAdapter(new ArrayList<>(), elemento -> abrirFormulario(elemento.getId()));
        rvColeccion.setLayoutManager(new LinearLayoutManager(this));
        rvColeccion.setAdapter(adapter);

        btnNuevo.setOnClickListener(v -> abrirFormulario(-1));
        btnAjustes.setOnClickListener(v ->
                startActivity(new Intent(this, AjustesActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarLista();
    }

    private void refrescarLista() {
        SharedPreferences prefs = getSharedPreferences(AjustesActivity.PREFS, MODE_PRIVATE);
        String nombre = prefs.getString(AjustesActivity.KEY_NOMBRE, "");
        boolean soloFavoritos = prefs.getBoolean(AjustesActivity.KEY_SOLO_FAVORITOS, false);

        if (nombre.isEmpty()) {
            setTitle(R.string.app_name);
        } else {
            setTitle(getString(R.string.titulo_con_usuario, nombre));
        }

        List<Elemento> visibles = new ArrayList<>();
        for (Elemento e : bd.listarTodos()) {
            if (!soloFavoritos || e.getFavorito() == 1) {
                visibles.add(e);
            }
        }

        adapter.actualizar(visibles);
        mostrarVacioSiHaceFalta(visibles);
    }

    private void abrirFormulario(int id) {
        Intent intent = new Intent(this, FormularioActivity.class);
        intent.putExtra(FormularioActivity.EXTRA_ID, id);
        startActivity(intent);
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
}