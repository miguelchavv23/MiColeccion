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

public class FormularioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTitulo;
    private Spinner spCategoria;
    private RadioGroup rgEstado;
    private CheckBox cbFavorito;
    private Button btnGuardar;
    private Button btnCancelar;

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

        btnGuardar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
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
        // Más adelante aquí se guardará en SQLite
        Toast.makeText(this, R.string.validacion_ok, Toast.LENGTH_SHORT).show();
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