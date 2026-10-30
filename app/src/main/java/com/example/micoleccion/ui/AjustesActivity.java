package com.example.micoleccion.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.micoleccion.R;

public class AjustesActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PREFS = "ajustes";
    public static final String KEY_NOMBRE = "nombre_usuario";
    public static final String KEY_SOLO_FAVORITOS = "solo_favoritos";

    private EditText etNombreUsuario;
    private CheckBox cbSoloFavoritos;
    private Button btnGuardarAjustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        cbSoloFavoritos = findViewById(R.id.cbSoloFavoritos);
        btnGuardarAjustes = findViewById(R.id.btnGuardarAjustes);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        etNombreUsuario.setText(prefs.getString(KEY_NOMBRE, ""));
        cbSoloFavoritos.setChecked(prefs.getBoolean(KEY_SOLO_FAVORITOS, false));

        btnGuardarAjustes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnGuardarAjustes) {
            guardar();
        }
    }

    private void guardar() {
        String nombre = etNombreUsuario.getText().toString();

        if (nombre.length() < 2 || nombre.length() > 30 || !nombre.equals(nombre.trim())) {
            etNombreUsuario.setError(getString(R.string.error_nombre));
            return;
        }

        SharedPreferences.Editor editor =
                getSharedPreferences(PREFS, MODE_PRIVATE).edit();
        editor.putString(KEY_NOMBRE, nombre);
        editor.putBoolean(KEY_SOLO_FAVORITOS, cbSoloFavoritos.isChecked());
        editor.apply();

        Toast.makeText(this, R.string.ajustes_guardados, Toast.LENGTH_SHORT).show();
        finish();
    }
}