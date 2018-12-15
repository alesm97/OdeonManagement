package com.example.alesm97.odeonmanagement;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alesm97.odeonmanagement.databinding.ActivityMainBinding;
import com.example.alesm97.odeonmanagement.models.Limpieza;
import com.example.alesm97.odeonmanagement.models.Sesion;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    public MainViewModel viewmodel;
    public ActivityMainBinding binding;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                switch (item.getItemId()) {
                    case R.id.bottom_menu_es:
                        changeToEsFragment();
                        return true;
                    case R.id.bottom_menu_limpieza:
                        changeToLimpiezaFragment();
                        return true;
                    case R.id.bottom_menu_pasen:
                        changeToPasenFragment();
                        return true;
                    case R.id.bottom_menu_incidencias:
                        changeToIncidenciasFragment();
                        return true;
                }
                return false;
            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);

        viewmodel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setVm(viewmodel);

        setContentView(R.layout.activity_main);

        TextView lblMainHour = findViewById(R.id.lblMainHour);
        TextView lblMainFecha = findViewById(R.id.lblMainFecha);

        viewmodel.watch.observe(this, lblMainHour::setText);
        viewmodel.fecha.observe(this, lblMainFecha::setText);


        //meterDatos();
        recoverDataEs();
        recoverDataLim();

        viewmodel.sesiones.observe(this, sesions -> {

            viewmodel.changeDataLists(sesions);
            viewmodel.launchSyncLists();
        });

        viewmodel.limpiezas.observe(this, new Observer<List<Limpieza>>() {
            @Override
            public void onChanged(@android.support.annotation.Nullable List<Limpieza> limpiezas) {
                if (viewmodel.limpiezaFragment.adapter != null){
                    viewmodel.limpiezaFragment.adapter.submitList(limpiezas);
                }
            }
        });

        changeToEsFragment();

        BottomNavigationView nav = findViewById(R.id.navigation);
        nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void recoverDataEs() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        fb.collection("sesiones").addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<Sesion> lista = new ArrayList<>();
            for (DocumentSnapshot object : queryDocumentSnapshots){
                lista.add(object.toObject(Sesion.class));
            }
            viewmodel.sesiones.postValue(lista);
        });
    }

    private void recoverDataLim() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        fb.collection("limpiezas").addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<Limpieza> lista = new ArrayList<>();
            for (DocumentSnapshot object : queryDocumentSnapshots){
                lista.add(object.toObject(Limpieza.class));
            }
            viewmodel.limpiezas.postValue(lista);
        });
    }



    private void changeToEsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,viewmodel.esFragment).commit();
        viewmodel.changeDataLists(viewmodel.sesiones.getValue());
    }

    private void changeToLimpiezaFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,viewmodel.limpiezaFragment).commit();
    }

    private void changeToPasenFragment(){
        //getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,viewmodel.pasenFragment).commit();
    }

    private void changeToIncidenciasFragment(){
        //getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,viewmodel.incidenciasFragment).commit();
    }

    public void meterDatos(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Sesion> sesiones = new ArrayList<>();
        List<Limpieza> limpiezas = new ArrayList<>();

        int hora = 10;
        int minutos = 10;
        int dia = 15;

        int[] anos = {2018,2018,2018,2018,2018,2018,2018,2018,2018,2018,2018,2018,2018,2018,2018};
        int[] meses = {1,2,3,4,5,6,7,8,9,10,11,12,14,15};
        int[] dias = {2,5,6,8,7,15,6,29,14,5,30,28,16,17,2};
        int[] horas = {16,17,16,19,18,20,22,23,21,19,18,16,17,17,19};
        //int[] minutos = {0,5,10,15,30,45,35,15,0,0,45,30,25,20,25};
        int[] sesion = {1,2,3,4,5,1,5,3,4,6,1,2,2,1,3};
        int[] sala = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
        String[] nombres = {"Pelicula 1","Pelicula 2","Pelicula 3","Pelicula 4","Pelicula 5","Pelicula 6","Pelicula 7","Pelicula 8","Pelicula 9","Pelicula 10","Pelicula 11","Pelicula 12","Pelicula 13","Pelicula 14","Pelicula 15"};

        /*for (int i = 0; i < 14; i++){
            sesiones.add(new Sesion(nombres[i],anos[0],meses[0],dia,hora,minutos+i,hora,minutos+i,sesion[i],sala[i]));
        }

        for(Sesion ses : sesiones){
            db.collection("sesiones").document(ses.getCodigo()).set(ses);
        }*/

        for (int i = 0; i < 14; i++){
            limpiezas.add(new Limpieza(nombres[i],anos[0],meses[0],dia,hora,minutos+i,sala[i],sesion[i],false));
        }

        for(Limpieza ses : limpiezas){
            db.collection("limpiezas").document(ses.getCodigo()).set(ses);
        }

        Toast.makeText(this, "Listo", Toast.LENGTH_SHORT).show();

    }





}
