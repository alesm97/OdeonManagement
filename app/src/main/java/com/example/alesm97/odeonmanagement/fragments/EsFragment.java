package com.example.alesm97.odeonmanagement.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alesm97.odeonmanagement.R;
import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EsFragment extends Fragment {


    MainViewModel viewModel;


    public EsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_es, container, false);
    }

}
