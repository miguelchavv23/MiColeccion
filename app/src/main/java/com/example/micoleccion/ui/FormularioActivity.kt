package com.example.micoleccion.ui

import android.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class FormularioActivity : AppCompatActivity(), View.OnClickListener {
    private var etTitulo: EditText? = null
    private var spCategoria: Spinner? = null
    private var rgEstado: RadioGroup? = null
    private var cbFavorito: CheckBox? = null
    private var btnGuardar: Button? = null
    private var btnCancelar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        etTitulo = findViewById<EditText?>(R.id.etTitulo)
        spCategoria = findViewById<Spinner?>(R.id.spCategoria)
        rgEstado = findViewById<RadioGroup?>(R.id.rgEstado)
        cbFavorito = findViewById<CheckBox?>(R.id.cbFavorito)
        btnGuardar = findViewById<Button?>(R.id.btnGuardar)
        btnCancelar = findViewById<Button?>(R.id.btnCancelar)

        btnGuardar!!.setOnClickListener(this)
        btnCancelar!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.getId()
        if (id == R.id.btnGuardar) {
            guardar()
        } else if (id == R.id.btnCancelar) {
            finish()
        }
    }

    private fun guardar() {
        if (!validar()) {
            return
        }
        // Más adelante aquí se guardará en SQLite
        Toast.makeText(this, R.string.validacion_ok, Toast.LENGTH_SHORT).show()
    }

    private fun validar(): Boolean {
        val titulo = etTitulo!!.getText().toString().trim { it <= ' ' }

        if (titulo.length < 2 || titulo.length > 60) {
            etTitulo!!.setError(getString(R.string.error_titulo))
            return false
        }
        if (spCategoria!!.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.error_categoria, Toast.LENGTH_SHORT).show()
            return false
        }
        if (rgEstado!!.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.error_estado, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}