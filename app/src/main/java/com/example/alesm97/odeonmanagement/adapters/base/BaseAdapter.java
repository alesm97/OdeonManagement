package com.example.alesm97.odeonmanagement.adapters.base;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.View;

import java.util.List;


public abstract class BaseAdapter<M, V extends BaseViewHolder>
        extends ListAdapter<M, V> {


    private View emptyView;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;

    public BaseAdapter(DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
        checkEmptyViewVisibility(getItemCount());
    }

    @Override
    public void submitList(List<M> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        super.submitList(list);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public void setEmptyView(@NonNull View emptyView) {
        this.emptyView = emptyView;
        checkEmptyViewVisibility(getItemCount());
    }

    private void checkEmptyViewVisibility(int size) {
        if (emptyView != null) {
            emptyView.setVisibility(size == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public interface OnItemClickListener {
        @SuppressWarnings("unused")
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        @SuppressWarnings({"SameReturnValue", "unused"})
        boolean onItemLongClick(View view, int position);
    }

    public View getEmptyView() {
        return emptyView;
    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

}
