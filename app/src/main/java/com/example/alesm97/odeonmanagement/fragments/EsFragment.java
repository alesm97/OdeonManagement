package com.example.alesm97.odeonmanagement.fragments;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alesm97.odeonmanagement.R;
import com.example.alesm97.odeonmanagement.adapters.ESAdapter;
import com.example.alesm97.odeonmanagement.models.Sesion;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class EsFragment extends Fragment {


    RecyclerView list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public RecyclerView getList() {
        return list;
    }

    public void setList(RecyclerView list) {
        this.list = list;
    }

    public ESAdapter getAdapter() {
        return adapter;
    }


    public ESAdapter adapter;


    public EsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_es,container,false);
        list = root.findViewById(R.id.lstEsEntrada);

        adapter = new ESAdapter();


        //List<Sesion> sesionesN = new ArrayList<>();
        //sesionesN.add(new Sesion("Pelicula 1",2018,12,12,12,12,5,1));


        adapter.setEmptyView(getLayoutInflater().inflate(R.layout.empty_view,container));
        //adapter.submitList(new ArrayList<>());
        //adapter.addItem(new Sesion("Pelicula",2018,11,25,14,25,5,5));
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));


        //Sesion sesion = new Sesion("Pelicula",2018,12,11,16,46,1,2);
        //db.collection("sesiones").document(sesion.getCodigo()).set(sesion);

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
