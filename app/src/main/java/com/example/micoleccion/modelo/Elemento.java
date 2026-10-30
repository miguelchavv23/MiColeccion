package com.example.micoleccion.modelo;
public class Elemento {

    private int id;
    private String titulo;
    private String categoria;
    private String estado;
    private int favorito; // 0 = no, 1 = sí

    public Elemento(int id, String titulo, String categoria, String estado, int favorito) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.estado = estado;
        this.favorito = favorito;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public String getEstado() { return estado; }
    public int getFavorito() { return favorito; }

    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setFavorito(int favorito) { this.favorito = favorito; }
}