package com.example.micoleccion.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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

        bd = new BaseDatos(this);

        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        idRecibido = getIntent().getIntExtra(EXTRA_ID, -1);
        if (idRecibido != -1) {
            Toast.makeText(this, getString(R.string.editando_elemento, idRecibido),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnGuardar) {
            guardar();
        } else if (id == R.id.btnCancelar) {
            finish();
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

        // La edición de un elemento existente se programa en el siguiente paso
        bd.insertar(new Elemento(0, titulo, categoria, estado, favorito));

        Toast.makeText(this, R.string.elemento_guardado, Toast.LENGTH_SHORT).show();
        finish();
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