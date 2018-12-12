package com.example.alesm97.odeonmanagement.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alesm97.odeonmanagement.R;
import com.example.alesm97.odeonmanagement.models.Sesion;

import java.util.List;

public class ESAdapter extends BaseAdapter<Sesion,ESAdapter.ViewHolder> {

    public ESAdapter() {
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.es_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(getItem(i));
    }

    class ViewHolder extends BaseViewHolder {

        private TextView lblNumSala;
        private TextView lblNombrePelicula;
        private TextView lblHora;

        ViewHolder(View itemView) {
            super(itemView, getOnItemClickListener(), getOnItemLongClickListener());
            lblNumSala = ViewCompat.requireViewById(itemView, R.id.lblEsNumSala);
            lblNombrePelicula = ViewCompat.requireViewById(itemView,R.id.lblEsTituloPelicula);
            lblHora = ViewCompat.requireViewById(itemView, R.id.lblEsHora);
        }

        void bind(Sesion sesion) {
            lblNombrePelicula.setText(sesion.getNombrePelicula());
            lblHora.setText(String.format("%d",sesion.getHora()));
            lblNumSala.setText(String.format("%d",sesion.getSala()));
        }

    }

}
