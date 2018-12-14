package com.example.alesm97.odeonmanagement.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alesm97.odeonmanagement.R;
import com.example.alesm97.odeonmanagement.adapters.ESAdapter;
import com.example.alesm97.odeonmanagement.adapters.ESAdapterSalida;
import com.example.alesm97.odeonmanagement.models.Sesion;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class EsFragment extends Fragment {


    RecyclerView list;
    RecyclerView listS;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ESAdapter adapter;
    public ESAdapterSalida adapterSalida;

    public EsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_es,container,false);
        list = root.findViewById(R.id.lstEsEntrada);
        listS = root.findViewById(R.id.lstEsSalida);
        list.addItemDecoration(new DividerItemDecoration(list.getContext(),DividerItemDecoration.VERTICAL));
        listS.addItemDecoration(new DividerItemDecoration(list.getContext(),DividerItemDecoration.VERTICAL));

        adapter = new ESAdapter();
        adapterSalida = new ESAdapterSalida();

        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_view_e,container));
        adapterSalida.setEmptyView(getLayoutInflater().inflate(R.layout.empty_view_s,container));

        list.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        listS.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
        list.setAdapter(adapter);
        listS.setAdapter(adapterSalida);

        return root;
    }



    private ArrayList<Sesion> getSesiones() {

        ArrayList<Sesion> resultado = new ArrayList<>();

        db.collection("sesiones").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot object:queryDocumentSnapshots){
                resultado.add(object.toObject(Sesion.class));
            }

        });

        return resultado;
    }

}
