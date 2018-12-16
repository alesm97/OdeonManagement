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
import com.example.alesm97.odeonmanagement.models.Pasen;

public class PasenAdapter extends BaseAdapter<Pasen,PasenAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Pasen> diffUtilCall = new DiffUtil.ItemCallback<Pasen>() {
        @Override
        public boolean areItemsTheSame(@NonNull Pasen pasen, @NonNull Pasen t1) {
            return pasen.getSala() == t1.getSala();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pasen pasen, @NonNull Pasen t1) {
            return pasen.equals(t1);
        }
    };

    public PasenAdapter() {
        super (diffUtilCall);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pasen_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(getItem(i));
    }

    class ViewHolder extends BaseViewHolder {

        private TextView lblNumSala;
        private TextView lblPasen;

        ViewHolder(View itemView) {
            super(itemView, getOnItemClickListener(), getOnItemLongClickListener());
            lblNumSala = ViewCompat.requireViewById(itemView, R.id.lblPasenSala);
            lblPasen = ViewCompat.requireViewById(itemView, R.id.lblPasenPasen);
        }

        void bind(Pasen pasen) {
            if (pasen != null){
                String text = "";
                if (pasen.isPasen()){
                    text = "PASEN";
                    lblPasen.setBackgroundColor(lblPasen.getContext().getResources().getColor(R.color.background_es_entrada));
                }else{
                    text = "ESPEREN";
                    lblPasen.setBackgroundColor(lblPasen.getContext().getResources().getColor(R.color.background_es_salida));
                }
                lblPasen.setText(text);
                lblNumSala.setText(String.format("%d",pasen.getSala()));
            }
        }

    }

}
