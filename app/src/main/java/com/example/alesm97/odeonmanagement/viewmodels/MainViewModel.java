package com.example.alesm97.odeonmanagement.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alesm97.odeonmanagement.models.Sesion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainViewModel extends AndroidViewModel {


    public MutableLiveData<String> fecha = new MutableLiveData<>();
    public MutableLiveData<List<Sesion>> sesiones = new MutableLiveData<>();
    public Watch watch = new Watch();


    public MainViewModel(@NonNull Application application) {
        super(application);
        watch.start();
        fecha.postValue(getFecha());
    }

    private String getFecha(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        return format.format(new Date());
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
