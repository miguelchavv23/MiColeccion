package com.example.micoleccion.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.micoleccion.modelo.Elemento;

import java.util.ArrayList;
import java.util.List;

public class BaseDatos extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "micoleccion.db";
    private static final int VERSION = 1;

    private static final String TABLA = "elementos";

    public BaseDatos(Context context) {
        super(context, NOMBRE_BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLA + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "titulo TEXT NOT NULL, "
                + "categoria TEXT NOT NULL, "
                + "estado TEXT NOT NULL, "
                + "favorito INTEGER NOT NULL DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA);
        onCreate(db);
    }

    public long insertar(Elemento e) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("titulo", e.getTitulo());
        valores.put("categoria", e.getCategoria());
        valores.put("estado", e.getEstado());
        valores.put("favorito", e.getFavorito());
        long id = db.insert(TABLA, null, valores);
        db.close();
        return id;
    }

    public List<Elemento> listarTodos() {
        List<Elemento> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLA, null, null, null, null, null, "titulo COLLATE NOCASE ASC");
        try {
            while (c.moveToNext()) {
                lista.add(new Elemento(
                        c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("titulo")),
                        c.getString(c.getColumnIndexOrThrow("categoria")),
                        c.getString(c.getColumnIndexOrThrow("estado")),
                        c.getInt(c.getColumnIndexOrThrow("favorito"))));
            }
        } finally {
            c.close();
            db.close();
        }
        return lista;
    }

    public Elemento obtener(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLA, null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        Elemento resultado = null;
        try {
            if (c.moveToFirst()) {
                resultado = new Elemento(
                        c.getInt(c.getColumnIndexOrThrow("id")),
                        c.getString(c.getColumnIndexOrThrow("titulo")),
                        c.getString(c.getColumnIndexOrThrow("categoria")),
                        c.getString(c.getColumnIndexOrThrow("estado")),
                        c.getInt(c.getColumnIndexOrThrow("favorito")));
            }
        } finally {
            c.close();
            db.close();
        }
        return resultado;
    }

    public int actualizar(Elemento e) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("titulo", e.getTitulo());
        valores.put("categoria", e.getCategoria());
        valores.put("estado", e.getEstado());
        valores.put("favorito", e.getFavorito());
        int filas = db.update(TABLA, valores, "id = ?",
                new String[]{String.valueOf(e.getId())});
        db.close();
        return filas;
    }

    public int borrar(int id) {
        SQLiteDatabase db = getWritableDatabase();
        int filas = db.delete(TABLA, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return filas;
    }

    // Devuelve true si ya existe un título igual (sin distinguir mayúsculas),
    // ignorando el elemento con id = excluirId (para cuando se edite)
    public boolean existeTitulo(String titulo, int excluirId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(TABLA, new String[]{"id"},
                "titulo = ? COLLATE NOCASE AND id <> ?",
                new String[]{titulo, String.valueOf(excluirId)},
                null, null, null);
        boolean existe = c.getCount() > 0;
        c.close();
        db.close();
        return existe;
    }
}