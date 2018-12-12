package com.example.alesm97.odeonmanagement;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.alesm97.odeonmanagement.databinding.ActivityMainBinding;
import com.example.alesm97.odeonmanagement.models.Sesion;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
                    case R.id.bottom_menu_limpieza:
                        changeToLimpiezaFragment();
                        return true;
                    case R.id.bottom_menu_pasen:

                        return true;
                    case R.id.bottom_menu_incidencias:

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

        Button btn = findViewById(R.id.btnLoad);
        btn.setOnClickListener(v -> {
            /*Toast.makeText(v.getContext(), "aaa", Toast.LENGTH_SHORT).show();
            Sesion sesion = new Sesion("Pelicula 2",2018,12,12,new Date().getMinutes(),new Date().getSeconds(),new Date().getSeconds(),1);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("sesiones").document(sesion.getCodigo()).set(sesion);*/
            recoverDataEs();
        });

        changeToEsFragment();
        recoverDataEs();
        viewmodel.sesiones.observe(this, sesions -> {
            viewmodel.loadList(sesions);
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //addDataToDB();

        //getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frgLayout,new EsFragment()).commit();
    }

    private void recoverDataEs() {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("sesiones").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Sesion> lista = new ArrayList<>();
                for (DocumentSnapshot object : queryDocumentSnapshots){
                    lista.add(object.toObject(Sesion.class));
                }
                viewmodel.sesiones.postValue(lista);
            }
        });
    }



    private void changeToEsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,viewmodel.esFragment).commit();
    }

    private void changeToLimpiezaFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,viewmodel.limpiezaFragment).commit();
    }

}
