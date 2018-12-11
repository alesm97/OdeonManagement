package com.example.alesm97.odeonmanagement;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alesm97.odeonmanagement.databinding.ActivityMainBinding;
import com.example.alesm97.odeonmanagement.fragments.EsFragment;
import com.example.alesm97.odeonmanagement.fragments.LimpiezaFragment;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    public MainViewModel viewmodel;
    public ActivityMainBinding binding;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                switch (item.getItemId()) {
                    case R.id.bottom_menu_pasen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,new EsFragment()).commit();
                        return true;
                    case R.id.bottom_menu_limpieza:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,new LimpiezaFragment()).commit();
                        return true;
                    case R.id.bottom_menu_incidencias:

                        return true;
                    case R.id.bottom_menu_es:

                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        viewmodel = new MainViewModel(this.getApplication());
        binding.setVm(viewmodel);
        setContentView(R.layout.activity_main);

        TextView lblMainHour = findViewById(R.id.lblMainHour);
        TextView lblMainFecha = findViewById(R.id.lblMainFecha);
        viewmodel.watch.observe(this, lblMainHour::setText);
        viewmodel.fecha.observe(this, lblMainFecha::setText);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
