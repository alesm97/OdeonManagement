package com.example.alesm97.odeonmanagement.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Collections;
import java.util.List;

public abstract class BaseAdapter<M, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    private List<M> data;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private View emptyView;
    private final RecyclerView.AdapterDataObserver adapterDataObserver =
            new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    checkEmptyViewVisibility();
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    checkEmptyViewVisibility();
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    super.onItemRangeRemoved(positionStart, itemCount);
                    checkEmptyViewVisibility();
                }
            };

    public void submitList(List<M> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<M> getList() {
        return data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setEmptyView(@NonNull View emptyView) {
        if (this.emptyView != null) {
            unregisterAdapterDataObserver(adapterDataObserver);
        }
        this.emptyView = emptyView;
        registerAdapterDataObserver(adapterDataObserver);
        checkEmptyViewVisibility();
    }

    private void checkEmptyViewVisibility() {
        if (emptyView != null) {
            emptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    @Override
    public final int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public M getItem(int position) {
        return data.get(position);
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(M item) {
        data.add(item);
        notifyItemInserted(data.size() - 1);
    }

    public void insertItem(M item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public void swapItems(int from, int to) {
        Collections.swap(data, from, to);
        notifyItemMoved(from, to);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }



}
