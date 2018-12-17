package com.example.alesm97.odeonmanagement.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.alesm97.odeonmanagement.models.UsuarioMin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginViewModel extends AndroidViewModel {

    public String user = "";
    public String pass = "";
    private String password = "";
    private String nickname = "";
    public MutableLiveData<Boolean> success = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();
    private UsuarioMin usuario = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        error.setValue("");
        success.setValue(false);
    }

    public void loginButtonOnClick(View v){
        
        
        if (pass.isEmpty()){
            password = "-";
        }else{
            password = pass;
        }

        if (user.equals("")){
            nickname = "-";
        }else{
            nickname = user;
        }

        db.collection("usuarios").document(nickname).get().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.get("nick")==null){
                    error.postValue("usuario no encontrado");
                }else{
                    error.setValue(documentSnapshot.get("nick").toString());
                    if (documentSnapshot.get("password").equals(password)){
                        success.setValue(true);
                    }else{
                        error.postValue("contraseña incorrecta");
                    }
                }

            }
        });
    }

}
