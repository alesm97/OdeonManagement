package com.example.alesm97.odeonmanagement;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.alesm97.odeonmanagement.databinding.ActivityMainBinding;
import com.example.alesm97.odeonmanagement.fragments.EsFragment;
import com.example.alesm97.odeonmanagement.fragments.Fragment2;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    public MainViewModel viewmodel;
    public ActivityMainBinding binding;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.bottom_menu_pasen:

                    return true;
                case R.id.bottom_menu_incidencias:

                    return true;
                case R.id.bottom_menu_es:
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frgMain,new Fragment2()).commit();
                    return true;
                case R.id.bottom_menu_limpieza:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        viewmodel = new MainViewModel(this.getApplication());
        binding.setVm(viewmodel);
        setContentView(R.layout.activity_main);

        //getSupportFragmentManager().beginTransaction().replace(R.id.frgLayout,new EsFragment()).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
