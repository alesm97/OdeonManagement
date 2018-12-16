package com.example.alesm97.odeonmanagement;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alesm97.odeonmanagement.adapters.base.BaseAdapter;
import com.example.alesm97.odeonmanagement.databinding.ActivityMainBinding;
import com.example.alesm97.odeonmanagement.models.Limpieza;
import com.example.alesm97.odeonmanagement.models.Pasen;
import com.example.alesm97.odeonmanagement.models.Sesion;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public MainViewModel viewmodel;
    public ActivityMainBinding binding;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                switch (item.getItemId()) {
                    case R.id.bottom_menu_es:
                        changeToEsFragment();
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
        FloatingActionButton btnLoad = findViewById(R.id.btnReload);

        /*btnLoad.setOnClickListener(v -> {
            if(viewmodel.pasenFragment.isVisible()){
                viewmodel.setPasenData();
            }else if(viewmodel.esFragment.isVisible()){
                viewmodel.setEsData();
            }
        });*/

        viewmodel.watch.observe(this, lblMainHour::setText);
        viewmodel.fecha.observe(this, lblMainFecha::setText);


        //meterDatos2();
        recoverDataEs();
        recoverDataPasen();

        addFragments();

        /*viewmodel.sesiones.observe(this, sesions -> {

            viewmodel.changeDataLists(sesions);

        });*/


        viewmodel.pasen.observe(this, sesions -> {
            viewmodel.setPasenData();
        });

        changeToEsFragment();


        viewmodel.entradas.observe(this, new Observer<List<Sesion>>() {
            @Override
            public void onChanged(@Nullable List<Sesion> sesions) {
                viewmodel.esFragment.adapter.submitList(sesions);
                //viewmodel.syncDataListE();
            }
        });

        viewmodel.salidas.observe(this, new Observer<List<Sesion>>() {
            @Override
            public void onChanged(@Nullable List<Sesion> sesions) {
                viewmodel.esFragment.adapterSalida.submitList(sesions);
                //viewmodel.syncDataListS();
            }
        });

        //viewmodel.threadLoadData();

        syncEsData();

        setOnClickPasen();

        BottomNavigationView nav = findViewById(R.id.navigation);
        nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void setOnClickPasen() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(viewmodel.pasenFragment.adapter == null){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                viewmodel.pasenFragment.adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Pasen pase = viewmodel.pasen.getValue().get(position);
                        pase.setPasen(!pase.isPasen());
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("pasen").document(String.format("%d",pase.getSala())).set(viewmodel.pasen.getValue().get(position));
                    }
                });

            }
        }).start();

    }


    private void addFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frgLayout,viewmodel.esFragment).hide(viewmodel.esFragment);
        transaction.commit();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frgLayout,viewmodel.pasenFragment).hide(viewmodel.pasenFragment);
        transaction.commit();
    }

    private void recoverDataPasen() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        fb.collection("pasen").addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<Pasen> lista = new ArrayList<>();
            for (DocumentSnapshot object : queryDocumentSnapshots){
                lista.add(object.toObject(Pasen.class));
            }
            viewmodel.pasen.postValue(lista);
        });
    }

    private void recoverDataEs() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

        fb.collection("entradas").whereEqualTo("anho",2018).whereEqualTo("mes",1).whereEqualTo("dia",15).addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<Sesion> lista = new ArrayList<>();
            for (DocumentSnapshot object : queryDocumentSnapshots){
                lista.add(object.toObject(Sesion.class));
            }
            viewmodel.entradas.setValue(lista);
        });

        fb.collection("salidas").whereEqualTo("anho",2018).whereEqualTo("mes",1).whereEqualTo("dia",15).addSnapshotListener((queryDocumentSnapshots, e) -> {
            List<Sesion> lista = new ArrayList<>();
            for (DocumentSnapshot object : queryDocumentSnapshots){
                lista.add(object.toObject(Sesion.class));
            }
            viewmodel.salidas.setValue(lista);
        });
    }

    private void changeToEsFragment() {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        if(!viewmodel.esFragment.isVisible()){
            if(viewmodel.pasenFragment.isVisible()){
                trans.hide(viewmodel.pasenFragment);
                trans.show(viewmodel.esFragment);
            }else{
                // TODO agregar fragmento incidencias
                trans.show(viewmodel.esFragment);
            }
        }
        trans.commit();
        //viewmodel.setEsData();
    }

    private void changeToPasenFragment(){
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        if(!viewmodel.pasenFragment.isVisible()){
            if(viewmodel.esFragment.isVisible()){
                trans.hide(viewmodel.esFragment);
                trans.show(viewmodel.pasenFragment);
            }else{
                trans.show(viewmodel.pasenFragment);
            }
        }
        trans.commit();
        //viewmodel.setPasenData();
    }

    private void observeEsLists() {

    }

    private void changeToLimpiezaFragment(){
        //getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,viewmodel.limpiezaFragment).commit();
        //observeLimList();
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

    public void meterDatos2(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sesiones").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Sesion> lista = new ArrayList<>();
                for (DocumentSnapshot object : queryDocumentSnapshots){
                    lista.add(object.toObject(Sesion.class));
                }
                for (Sesion s : lista){
                    db.collection("entradas").document(s.getCodigo()).set(s);
                    db.collection("salidas").document(s.getCodigo()).set(s);
                }

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void syncEsData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (viewmodel.esFragment.adapter == null && viewmodel.esFragment.adapterSalida == null) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                while (!Thread.currentThread().isInterrupted()) {

                    List<Sesion> objectos = viewmodel.entradas.getValue();

                    if (objectos != null) {
                        List<Sesion> aEliminar = new ArrayList<>();
                        for (Sesion sesion : objectos) {
                            LocalTime time1 = LocalTime.now();
                            LocalTime time2 = LocalTime.of(sesion.getHoraE(), sesion.getMinutosE());

                            if (time1.isAfter(time2.plusMinutes(2))) {
                                aEliminar.add(sesion);
                            }
                        }

                        for (Sesion s : aEliminar) {
                            db.collection("entradas").document(s.getCodigo()).delete();
                        }


                        objectos = viewmodel.salidas.getValue();
                        if (objectos != null) {
                            aEliminar.clear();
                            for (Sesion sesion : objectos) {
                                LocalTime time1 = LocalTime.now();
                                LocalTime time2 = LocalTime.of(sesion.getHoraS(), sesion.getMinutosS());

                                if (time1.isAfter(time2.plusMinutes(2))) {
                                    aEliminar.add(sesion);
                                }
                            }

                            for (Sesion s : aEliminar) {
                                db.collection("salidas").document(s.getCodigo()).delete();
                            }
                        }


                        //viewmodel.updateEs();
                        try {
                            Thread.sleep(30000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }




    public void launchSyncLists() {
        Thread syncE = new Thread(new Runnable() {
            @Override
            public void run() {
                viewmodel.syncDataListE();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        syncE.start();

        Thread syncS = new Thread(new Runnable() {
            @Override
            public void run() {
                viewmodel.syncDataListS();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        syncS.start();
    }


}
