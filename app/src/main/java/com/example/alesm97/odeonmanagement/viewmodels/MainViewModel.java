package com.example.alesm97.odeonmanagement.viewmodels;

import android.annotation.TargetApi;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.example.alesm97.odeonmanagement.fragments.EsFragment;
import com.example.alesm97.odeonmanagement.fragments.LimpiezaFragment;
import com.example.alesm97.odeonmanagement.models.Sesion;

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

    private List<Sesion> entradas = new ArrayList<>();
    private List<Sesion> salidas = new ArrayList<>();

    public Watch watch = new Watch();

    public EsFragment esFragment;
    public LimpiezaFragment limpiezaFragment;

    public MutableLiveData<Filtro> filtro = new MutableLiveData<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
        watch.start();
        fecha.postValue(getFecha());

        esFragment = new EsFragment();
        limpiezaFragment = new LimpiezaFragment();



        filtro.setValue(Filtro.TODAS);


    }

    public void loadList(List<Sesion> sesiones){
        esFragment.adapter.submitList(sesiones);
    }

    private String getFecha(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        return format.format(new Date());
    }




    public void changeFilter (){
        if (filtro.getValue() == Filtro.PARES){
            //filtrarPares();
        }else if (filtro.getValue() == Filtro.IMPARES){
            //filtrarImpares();
        }else{
            //filtrarTodas();
        }
    }


    public void changeDataLists(List<Sesion> sesiones){
        entradas.clear();
        entradas.addAll(sesiones);
        salidas.clear();
        salidas.addAll(sesiones);
        Collections.sort(entradas, new Comparator<Sesion>() {
            @Override
            public int compare(Sesion o1, Sesion o2) {
                return 0;
            }
        });
        esFragment.adapter.submitList(entradas);
        esFragment.adapterSalida.submitList(salidas);
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void syncDataListE(){

        int contador = 0;
        List<Sesion> aEliminar = new ArrayList<>();
        for (Sesion sesion : entradas){
            LocalTime time1 = LocalTime.now();
            LocalTime time2 = LocalTime.of(sesion.getHoraE(),sesion.getMinutosE());

            if(time1.isAfter(time2.plusMinutes(15))){
                aEliminar.add(sesion);
                contador++;
            }
        }

        if(contador>0){
            entradas.removeAll(aEliminar);
            esFragment.adapter.submitList(entradas);
        }

        /*
        new Thread(() -> {
            int contador = 0;
            for (Sesion sesion : entradas){
                LocalTime time1 = LocalTime.now();
                LocalTime time2 = LocalTime.of(sesion.getHoraE(),sesion.getMinutosE());

                if(time1.isAfter(time2.plusMinutes(15))){
                    entradas.remove(sesion);
                    contador++;
                }
            }

            if(contador>0){
                esFragment.adapter.submitList(entradas);
            }

            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();*/
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void syncDataListS(){

        int contador = 0;
        List<Sesion> aEliminar = new ArrayList<>();
        for (Sesion sesion : salidas){
            LocalTime time1 = LocalTime.now();
            LocalTime time2 = LocalTime.of(sesion.getHoraE(),sesion.getMinutosE());

            if(time1.isAfter(time2.plusMinutes(15))){
                aEliminar.add(sesion);
                contador++;
            }
        }

        if(contador>0){
            salidas.removeAll(aEliminar);
            esFragment.adapterSalida.submitList(salidas);
        }

        /*
        new Thread(() -> {
            int contador = 0;
            for (Sesion sesion : salidas){
                LocalTime time1 = LocalTime.now();
                LocalTime time2 = LocalTime.of(sesion.getHoraE(),sesion.getMinutosE());

                if(time1.isAfter(time2.plusMinutes(15))){
                    entradas.remove(sesion);
                    contador++;
                }
            }

            if(contador>0){
                esFragment.adapterSalida.submitList(entradas);
            }
        }).start();*/
    }

    public enum Filtro {

        TODAS, PARES, IMPARES
    }

    public class Watch extends MutableLiveData<String> {

        private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        private Thread thread;
        private int numero = 0;

        public void start(){
            if (thread == null || !thread.isAlive()){
                thread = new Thread(() -> {
                    while(!Thread.currentThread().isInterrupted()){
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
            return String.format("%d",numero);
        }
    }

}
