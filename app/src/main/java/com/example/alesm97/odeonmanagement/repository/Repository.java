package com.example.alesm97.odeonmanagement.repository;

import com.example.alesm97.odeonmanagement.viewmodels.MainViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class Repository {

    private static Repository repository;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Repository(MainViewModel vm){
        repository = new Repository(vm);
    }


}
