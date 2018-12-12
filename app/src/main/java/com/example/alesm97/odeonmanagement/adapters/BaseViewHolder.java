package com.example.alesm97.odeonmanagement.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected BaseViewHolder(View itemView){
        super(itemView);
        /*
        if (onItemClickListener != null) {
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        if (onItemLongClickListener != null) {
            itemView.setOnLongClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    return onItemLongClickListener.onItemLongClick(v, getAdapterPosition());
                }
                return false;
            });
        }*/
    }



}
