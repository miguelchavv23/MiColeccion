package com.example.micoleccion.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.micoleccion.R;
import com.example.micoleccion.datos.BaseDatos;
import com.example.micoleccion.modelo.Elemento;

public class FormularioActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_ID = "id";

    private EditText etTitulo;
    private Spinner spCategoria;
    private RadioGroup rgEstado;
    private CheckBox cbFavorito;
    private Button btnGuardar;
    private Button btnCancelar;
    private Button btnEliminar;

    private BaseDatos bd;
    private int idRecibido = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        etTitulo = findViewById(R.id.etTitulo);
        spCategoria = findViewById(R.id.spCategoria);
        rgEstado = findViewById(R.id.rgEstado);
        cbFavorito = findViewById(R.id.cbFavorito);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEliminar = findViewById(R.id.btnEliminar);

        bd = new BaseDatos(this);

        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);

        idRecibido = getIntent().getIntExtra(EXTRA_ID, -1);
        if (idRecibido != -1) {
            cargarElemento();
        } else {
            setTitle(R.string.titulo_formulario);
        }
    }

    private void cargarElemento() {
        Elemento e = bd.obtener(idRecibido);
        if (e == null) {
            Toast.makeText(this, R.string.error_no_encontrado, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setTitle(R.string.titulo_editar);
        btnEliminar.setVisibility(View.VISIBLE);

        etTitulo.setText(e.getTitulo());
        cbFavorito.setChecked(e.getFavorito() == 1);

        String[] categorias = getResources().getStringArray(R.array.categorias);
        for (int i = 0; i < categorias.length; i++) {
            if (categorias[i].equals(e.getCategoria())) {
                spCategoria.setSelection(i);
                break;
            }
        }

        if (e.getEstado().equals(getString(R.string.estado_pendiente))) {
            rgEstado.check(R.id.rbPendiente);
        } else {
            rgEstado.check(R.id.rbCompletado);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnGuardar) {
            guardar();
        } else if (id == R.id.btnCancelar) {
            finish();
        } else if (id == R.id.btnEliminar) {
            confirmarEliminar();
        }
    }

    private void guardar() {
        if (!validar()) {
            return;
        }

        String titulo = etTitulo.getText().toString().trim();

        if (bd.existeTitulo(titulo, idRecibido)) {
            etTitulo.setError(getString(R.string.error_duplicado));
            return;
        }

        String categoria = spCategoria.getSelectedItem().toString();
        String estado = rgEstado.getCheckedRadioButtonId() == R.id.rbPendiente
                ? getString(R.string.estado_pendiente)
                : getString(R.string.estado_completado);
        int favorito = cbFavorito.isChecked() ? 1 : 0;

        if (idRecibido == -1) {
            bd.insertar(new Elemento(0, titulo, categoria, estado, favorito));
            Toast.makeText(this, R.string.elemento_guardado, Toast.LENGTH_SHORT).show();
        } else {
            bd.actualizar(new Elemento(idRecibido, titulo, categoria, estado, favorito));
            Toast.makeText(this, R.string.elemento_actualizado, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void confirmarEliminar() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirmar_titulo)
                .setMessage(R.string.confirmar_mensaje)
                .setPositiveButton(R.string.si, (dialog, which) -> {
                    bd.borrar(idRecibido);
                    Toast.makeText(this, R.string.elemento_eliminado, Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private boolean validar() {
        String titulo = etTitulo.getText().toString().trim();

        if (titulo.length() < 2 || titulo.length() > 60) {
            etTitulo.setError(getString(R.string.error_titulo));
            return false;
        }
        if (spCategoria.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.error_categoria, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (rgEstado.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.error_estado, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}