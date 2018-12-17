package com.example.alesm97.odeonmanagement.viewmodels;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.example.alesm97.odeonmanagement.adapters.base.BaseAdapter;
import com.example.alesm97.odeonmanagement.fragments.EsFragment;
import com.example.alesm97.odeonmanagement.fragments.IncidenciasFragment;
import com.example.alesm97.odeonmanagement.fragments.PasenFragment;
import com.example.alesm97.odeonmanagement.models.Incidencia;
import com.example.alesm97.odeonmanagement.models.Pasen;
import com.example.alesm97.odeonmanagement.models.Sesion;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainViewModel extends AndroidViewModel {


    public MutableLiveData<String> fecha = new MutableLiveData<>();
    public MutableLiveData<List<Sesion>> sesiones = new MutableLiveData<>();

    public MutableLiveData<List<Pasen>> pasen = new MutableLiveData<>();

    public MutableLiveData<List<Sesion>> entradas = new MutableLiveData<>();
    public MutableLiveData<List<Sesion>> salidas = new MutableLiveData<>();
    public MutableLiveData<List<Incidencia>> incidencias = new MutableLiveData<>();

    private List<Pasen> listaPasen = new ArrayList<>();

    public Watch watch = new Watch();

    public EsFragment esFragment;
    public PasenFragment pasenFragment;
    public IncidenciasFragment incidenciasFragment;

    public MutableLiveData<Filtro> filtro = new MutableLiveData<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
        watch.start();
        fecha.postValue(getFecha());

        esFragment = new EsFragment();
        pasenFragment = new PasenFragment();
        incidenciasFragment = new IncidenciasFragment();

        filtro.setValue(Filtro.TODAS);
    }


    private String getFecha() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        return format.format(new Date());
    }


    public List<Sesion> sortDataListE(List<Sesion> sesiones) {
        if (sesiones != null) {

            Collections.sort(sesiones, new Comparator<Sesion>() {

                @Override
                public int compare(Sesion o1, Sesion o2) {
                    int c = Integer.compare(o1.getHoraE(), o2.getHoraE());

                    if (c == 0) {
                        c = Integer.compare(o1.getMinutosE(), o2.getMinutosE());
                    }

                    return c;
                }
            });


        }
        return sesiones;
    }

    public List<Sesion> sortDataListS(List<Sesion> sesiones) {
        if (sesiones != null) {

            Collections.sort(sesiones, new Comparator<Sesion>() {

                @Override
                public int compare(Sesion o1, Sesion o2) {
                    int c = Integer.compare(o1.getHoraE(), o2.getHoraE());

                    if (c == 0) {
                        c = Integer.compare(o1.getMinutosE(), o2.getMinutosE());
                    }

                    return c;
                }
            });
        }

        return sesiones;

    }

    @TargetApi(Build.VERSION_CODES.O)
    public void syncDataListE() {

        int contador = 0;
        List<Sesion> objectos = entradas.getValue();
        if (objectos != null){
            List<Sesion> aEliminar = new ArrayList<>();
            for (Sesion sesion : objectos) {
                LocalTime time1 = LocalTime.now();
                LocalTime time2 = LocalTime.of(sesion.getHoraE(), sesion.getMinutosE());

                if (time1.isAfter(time2.plusMinutes(2))) {
                    aEliminar.add(sesion);
                    contador++;
                }
            }

            if (contador > 0) {
                objectos.removeAll(aEliminar);
            }

            //esFragment.adapter.submitList(objectos);
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    public void updateEs() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        List<Sesion> objectos = entradas.getValue();
        if (objectos != null) {
            List<Sesion> aEliminar = new ArrayList<>();
            for (Sesion sesion : objectos) {
                LocalTime time1 = LocalTime.now();
                LocalTime time2 = LocalTime.of(sesion.getHoraE(), sesion.getMinutosE());

                if (time1.isAfter(time2.plusMinutes(2))) {
                    aEliminar.add(sesion);
                }
            }

            for(Sesion s :objectos){
                db.collection("entradas").document(s.getCodigo()).delete();
            }


            objectos = salidas.getValue();
            if (objectos != null) {
                aEliminar.clear();
                for (Sesion sesion : objectos) {
                    LocalTime time1 = LocalTime.now();
                    LocalTime time2 = LocalTime.of(sesion.getHoraE(), sesion.getMinutosE());

                    if (time1.isAfter(time2.plusMinutes(2))) {
                        aEliminar.add(sesion);
                    }
                }

                for(Sesion s :objectos){
                    db.collection("salidas").document(s.getCodigo()).delete();
                }
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    public void syncDataListS() {

        int contador = 0;
        List<Sesion> aEliminar = new ArrayList<>();
        List<Sesion> objetos = salidas.getValue();
        if (objetos != null){
            for (Sesion sesion : objetos) {
                LocalTime time1 = LocalTime.now();
                LocalTime time2 = LocalTime.of(sesion.getHoraS(), sesion.getMinutosS());

                if (time1.isAfter(time2.plusMinutes(2))) {
                    aEliminar.add(sesion);
                    contador++;
                }
            }

            if (contador > 0) {
                objetos.removeAll(aEliminar);
            }

            esFragment.adapterSalida.submitList(objetos);
        }
    }

    public void setEsData() {
        if (esFragment.adapter != null) {
            syncDataListE();
            syncDataListS();
            esFragment.adapter.submitList(entradas.getValue());
            esFragment.adapterSalida.submitList(salidas.getValue());
        }

    }

    public void setPasenData() {
        if (pasenFragment.adapter != null) {
            pasenFragment.adapter.submitList(pasen.getValue());
        }
    }

    public void threadLoadData() {

        if (esFragment.adapter != null) {
            esFragment.adapter.submitList(entradas.getValue());
        }
        if (esFragment.adapterSalida != null) {
            esFragment.adapterSalida.submitList(salidas.getValue());
        }

    }


    public enum Filtro {

        TODAS, PARES, IMPARES
    }

    public class Watch extends MutableLiveData<String> {

        private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        private Thread thread;
        private int numero = 0;

        public void start() {
            if (thread == null || !thread.isAlive()) {
                thread = new Thread(() -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        postValue(simpleDateFormat.format(new Date()));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        }

        public void stop() {
            if (isRunning()) {
                thread.interrupt();
            }
        }

        public boolean isRunning() {
            return thread != null && thread.isAlive();
        }

        @Nullable
        @Override
        public String getValue() {
            return String.format("%d", numero);
        }
    }

}
