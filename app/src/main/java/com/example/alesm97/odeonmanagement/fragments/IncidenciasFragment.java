package com.example.alesm97.odeonmanagement.fragments;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alesm97.odeonmanagement.R;
import com.example.alesm97.odeonmanagement.adapters.IncidenciaAdapter;
import com.example.alesm97.odeonmanagement.models.Incidencia;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncidenciasFragment extends Fragment {

    RecyclerView list;
    public IncidenciaAdapter adapter;
    Spinner spinner;
    Button button;
    EditText mensaje;


    public IncidenciasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_incidencias,container,false);
        list = root.findViewById(R.id.lstIncidencias);
        list.addItemDecoration(new DividerItemDecoration(list.getContext(),DividerItemDecoration.VERTICAL));
        spinner = root.findViewById(R.id.spinnerCritico);
        List<Integer> options = new ArrayList<>();
        options.add(1);
        options.add(2);
        options.add(3);
        spinner.setAdapter(new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, options));

        button = root.findViewById(R.id.btnAddIncidencia);
        mensaje = root.findViewById(R.id.txtIncidenciasMensaje);

        mensaje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0){
                    button.setEnabled(false);
                }else{
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                LocalTime time = LocalTime.now();
                int critico = (int) spinner.getSelectedItem();

                Incidencia incidencia = new Incidencia(String.format("%d-%d-%d-%d-%d-%d-%d",2018,12,17, time.getHour(),time.getMinute(),time.getSecond(),critico),mensaje.getText().toString(),critico);
                db.collection("incidencias").document(incidencia.getCodigo()).set(incidencia);
                Toast.makeText(getView().getContext(), "Añadido correctamente", Toast.LENGTH_SHORT).show();
                mensaje.setText("");
            }
        });

        spinner.setSelection(0);

        adapter = new IncidenciaAdapter();

        list.setLayoutManager(new LinearLayoutManager(container.getContext(),LinearLayoutManager.VERTICAL,false));
        list.setAdapter(adapter);

        return root;
    }

}
