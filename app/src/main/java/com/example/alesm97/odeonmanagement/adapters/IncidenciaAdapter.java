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
import com.example.alesm97.odeonmanagement.models.Incidencia;

public class IncidenciaAdapter extends BaseAdapter<Incidencia,IncidenciaAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Incidencia> diffUtilCall = new DiffUtil.ItemCallback<Incidencia>() {
        @Override
        public boolean areItemsTheSame(@NonNull Incidencia sesion, @NonNull Incidencia t1) {
            return sesion.getCodigo().equals(t1.getCodigo());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Incidencia sesion, @NonNull Incidencia t1) {
            return sesion.equals(t1);
        }
    };

    public IncidenciaAdapter() {
        super (diffUtilCall);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incidencia_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(getItem(i));
    }

    class ViewHolder extends BaseViewHolder {

        private TextView lblincidenciaItemFecha;
        private TextView lblIncidenciaMensaje;

        ViewHolder(View itemView) {
            super(itemView, getOnItemClickListener(), getOnItemLongClickListener());
            lblincidenciaItemFecha = ViewCompat.requireViewById(itemView,R.id.lblincidenciaItemFecha);
            lblIncidenciaMensaje = ViewCompat.requireViewById(itemView, R.id.lblIncidenciaMensaje);
        }

        void bind(Incidencia sesion) {
            if (sesion != null){
                lblincidenciaItemFecha.setText(sesion.getCodigo());
                lblIncidenciaMensaje.setText(sesion.getMensaje());
            }
        }

    }

}
