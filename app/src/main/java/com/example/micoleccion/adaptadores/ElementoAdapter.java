package com.example.micoleccion.adaptadores;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.micoleccion.R;
import com.example.micoleccion.modelo.Elemento;

import java.util.List;

public class ElementoAdapter extends RecyclerView.Adapter<ElementoAdapter.ElementoViewHolder> {

    private List<Elemento> lista;

    public ElementoAdapter(List<Elemento> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_elemento, parent, false);
        return new ElementoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementoViewHolder holder, int position) {
        Elemento e = lista.get(position);
        holder.tvTitulo.setText(e.getTitulo());
        holder.tvCategoriaEstado.setText(e.getCategoria() + " · " + e.getEstado());
        holder.tvFavorito.setVisibility(e.getFavorito() == 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void actualizar(List<Elemento> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    static class ElementoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        TextView tvCategoriaEstado;
        TextView tvFavorito;

        ElementoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvCategoriaEstado = itemView.findViewById(R.id.tvCategoriaEstado);
            tvFavorito = itemView.findViewById(R.id.tvFavorito);
        }
    }
}