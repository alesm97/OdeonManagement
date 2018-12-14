package com.example.alesm97.odeonmanagement.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.alesm97.odeonmanagement.fragments.EsFragment;
import com.example.alesm97.odeonmanagement.fragments.LimpiezaFragment;
import com.example.alesm97.odeonmanagement.models.Sesion;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainViewModel extends AndroidViewModel {


    public MutableLiveData<String> fecha = new MutableLiveData<>();
    public MutableLiveData<List<Sesion>> sesiones = new MutableLiveData<>();
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
        //esFragment.adapter.submitList(sesiones);
    }

    private String getFecha(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        return format.format(new Date());
    }

    /*public void click(View view) {

        Toast.makeText(view.getContext(), "aaa", Toast.LENGTH_SHORT).show();

        Sesion sesion = new Sesion("Pelicula 2",2018,12,12,new Date().getMinutes(),new Date().getSeconds(), horaS, minutosS, 1,1);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sesiones").document(sesion.getCodigo()).set(sesion);

        List<Sesion> lista = new ArrayList<>();

        for (int contador = 1; contador < 12 ; contador ++){
            lista.add(new Sesion(String.format("Pelicula %d",contador),2018,12,12,12,12,5,contador));
        }

        for(Sesion sesion : lista) {
            db.collection("sesiones").document(sesion.getCodigo()).set(sesion).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this, "Agregado", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }*/

    public enum Filtro {

        TODAS, PARES, IMPARES
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
