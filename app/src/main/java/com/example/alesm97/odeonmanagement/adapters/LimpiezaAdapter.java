package com.example.alesm97.odeonmanagement.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alesm97.odeonmanagement.R;
import com.example.alesm97.odeonmanagement.adapters.base.BaseAdapter;
import com.example.alesm97.odeonmanagement.adapters.base.BaseViewHolder;
import com.example.alesm97.odeonmanagement.models.Limpieza;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;

public class LimpiezaAdapter extends BaseAdapter<Limpieza, LimpiezaAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Limpieza> diffUtilCall = new DiffUtil.ItemCallback<Limpieza>() {
        @Override
        public boolean areItemsTheSame(@NonNull Limpieza limpieza, @NonNull Limpieza t1) {
            return limpieza.getCodigo().equals(t1.getCodigo());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Limpieza limpieza, @NonNull Limpieza t1) {
            return limpieza.equals(t1);
        }
    };

    public LimpiezaAdapter() {
        super(diffUtilCall);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.limpieza_item, viewGroup,false));
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
            lblNumSala = ViewCompat.requireViewById(itemView, R.id.lblLimSala);
            lblNombrePelicula = ViewCompat.requireViewById(itemView,R.id.lblLimNombrePelicula);
            lblHora = ViewCompat.requireViewById(itemView, R.id.lblLimHora);
        }

        void bind(Limpieza limpieza) {
            if (limpieza != null){
                lblNombrePelicula.setText(limpieza.getNombrePelicula());
                lblHora.setText(String.format("%02d:%02d",limpieza.getHora(),limpieza.getMinutos()));
                lblNumSala.setText(String.format("%d",limpieza.getSala()));
            }
        }

    }
}
