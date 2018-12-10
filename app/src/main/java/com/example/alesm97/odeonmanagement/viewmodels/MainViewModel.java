package com.example.alesm97.odeonmanagement.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.alesm97.odeonmanagement.models.UsuarioMin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainViewModel extends AndroidViewModel {


    public MutableLiveData<String> hour = new MutableLiveData<>();
    public MutableLiveData<String> fecha = new MutableLiveData<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
        fecha.postValue(new Date().toString());
    }

    public void click (View v){
        
    }

    /*
    public class Watch extends MutableLiveData<String> {

        private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        private Thread thread;
        private int numero = 0;

        public void start(){
            if (thread == null || !thread.isAlive()){
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(!Thread.currentThread().isInterrupted()){
                            numero++;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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
    }*/

}
