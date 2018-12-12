package com.example.alesm97.odeonmanagement;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alesm97.odeonmanagement.databinding.ActivityMainBinding;
import com.example.alesm97.odeonmanagement.fragments.EsFragment;
import com.example.alesm97.odeonmanagement.fragments.LimpiezaFragment;
import com.example.alesm97.odeonmanagement.models.Sesion;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    public MainViewModel viewmodel;
    public ActivityMainBinding binding;
    private EsFragment esFragment = new EsFragment();
    private LimpiezaFragment limpiezaFragment = new LimpiezaFragment();


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

        changeToEsFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        addDataToDB();

        //getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frgLayout,new EsFragment()).commit();
    }

    private void addDataToDB() {
        /*List<Sesion> lista = new ArrayList<>();

        for (int contador = 1; contador < 12 ; contador ++){
            lista.add(new Sesion(String.format("Pelicula %d",contador),2018,12,12,12,12,5,contador));
        }*/

        Sesion sesion = new Sesion("Pelicula 2",2018,12,12,15,25,6,1);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("sesiones").document(sesion.getCodigo()).set(sesion);

        /*for(Sesion sesion : lista) {
            db.collection("sesiones").document(sesion.getCodigo()).set(sesion).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this, "Agregado", Toast.LENGTH_SHORT).show();
                }
            });
        }*/

    }

    private void changeToEsFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,esFragment).commit();
    }

    private void changeToLimpiezaFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,limpiezaFragment).commit();
    }

}
