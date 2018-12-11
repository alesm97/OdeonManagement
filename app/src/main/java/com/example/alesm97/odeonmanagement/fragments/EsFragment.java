package com.example.alesm97.odeonmanagement.fragments;


import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
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
import java.util.Date;
import java.util.List;
import java.util.Map;


public class EsFragment extends Fragment {


    MainViewModel viewmodel;
    List<Sesion> sesiones = new ArrayList<>();
    RecyclerView list;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public EsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewmodel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        //sesiones = getSesiones();


        View view = inflater.inflate(R.layout.fragment_es,container,false);
        list = view.findViewById(R.id.lstEsEntrada);


        //Sesion sesion = new Sesion("Pelicula",2018,12,11,16,46,1,2);
        //db.collection("sesiones").document(sesion.getCodigo()).set(sesion);

        return view;
    }



    private ArrayList<Sesion> getSesiones() {

        db.collection("sesiones").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot object:queryDocumentSnapshots){
                    sesiones.add(object.toObject(Sesion.class));
                }


            }
        });

        return null;
    }

    public void setList(View empty){
        ESAdapter adapter = new ESAdapter();

        adapter.setEmptyView(empty);
        list.setAdapter(adapter);
    }

}
