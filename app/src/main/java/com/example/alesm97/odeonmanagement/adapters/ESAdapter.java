package com.example.alesm97.odeonmanagement.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alesm97.odeonmanagement.R;
import com.example.alesm97.odeonmanagement.adapters.base.BaseAdapter;
import com.example.alesm97.odeonmanagement.adapters.base.BaseViewHolder;
import com.example.alesm97.odeonmanagement.models.Sesion;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;

public class ESAdapter extends BaseAdapter<Sesion,ESAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Sesion> diffUtilCall = new DiffUtil.ItemCallback<Sesion>() {
        @Override
        public boolean areItemsTheSame(@NonNull Sesion sesion, @NonNull Sesion t1) {
            return sesion.getCodigo().equals(t1.getCodigo());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Sesion sesion, @NonNull Sesion t1) {
            return sesion.equals(t1);
        }
    };

    public ESAdapter() {
        super (diffUtilCall);
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
            if (sesion != null){
                lblNombrePelicula.setText(sesion.getNombrePelicula());
                lblHora.setText(String.format("%02d:%02d",sesion.getHoraE(),sesion.getMinutosE()));
                lblNumSala.setText(String.format("%d",sesion.getSala()));
            }
        }

    }

}
