package com.sanus.sanus.domain.configuration.interactor;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sanus.sanus.domain.configuration.presenter.AjustesPresenter;

import java.util.HashMap;
import java.util.Map;

public class AjustesInteractorImpl implements AjustesInteractor {
    private AjustesPresenter presenter;
    private String idUser;


    public AjustesInteractorImpl(AjustesPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        showAccount();
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        presenter.goSplash();
    }

    @Override
    public void onClickActive(final String activo) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            idUser = user.getUid();
        }

        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        final FirebaseFirestore mFirestoreU = FirebaseFirestore.getInstance();
        mFirestore.collection("usuarios").document(idUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                String apellido = documentSnapshot.getString("apellido");
                String avatar = documentSnapshot.getString("avatar");
                String edad = documentSnapshot.getString("edad");
                String nombre = documentSnapshot.getString("nombre");
                String sexo = documentSnapshot.getString("sexo");
                String tipo = documentSnapshot.getString("tipo");

                Map<String, String> userMap = new HashMap<>();
                userMap.put("apellido", apellido);
                userMap.put("avatar", avatar);
                userMap.put("edad", edad);
                userMap.put("nombre", nombre);
                userMap.put("sexo", sexo);
                userMap.put("tipo", tipo);
                userMap.put("estado", activo);

                mFirestoreU.collection("usuarios").document(idUser).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Ajustes", "exitos");
                    }
                });
            }
        });
    }


    private void showAccount() {


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        if (user != null) {
            idUser = user.getUid();
        }

        mFirestore.collection("usuarios").document(idUser).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                String nombre = documentSnapshot.getString("nombre");
                String apellido = documentSnapshot.getString("apellido");

                presenter.showName(nombre.concat(" " + apellido));

                final String storageImage = documentSnapshot.getString("avatar");

                if (storageImage == null || storageImage.isEmpty()) {
                    return;
                }

                final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://sanus-27.appspot.com/avatar/");
                storageReference.child(storageImage).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        presenter.showPhoto(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.d(String.valueOf(this), "no hay conexion");
                    }
                });


            }
        });
    }
}
