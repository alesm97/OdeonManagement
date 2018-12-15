package com.example.alesm97.odeonmanagement.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alesm97.odeonmanagement.R;
import com.example.alesm97.odeonmanagement.adapters.LimpiezaAdapter;


public class LimpiezaFragment extends Fragment {

    RecyclerView list;
    public LimpiezaAdapter adapter;


    public LimpiezaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_limpieza,container);
        list = root.findViewById(R.id.lstLimpieza);
        list.addItemDecoration(new DividerItemDecoration(list.getContext(),DividerItemDecoration.VERTICAL));

        list.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        list.setAdapter(adapter);


        return inflater.inflate(R.layout.fragment_limpieza, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LimpiezaAdapter();
    }
}
