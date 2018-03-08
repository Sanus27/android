package com.sanus.sanus.domain.account.complete.interactor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanus.sanus.R;
import com.sanus.sanus.data.repository.firebase.entity.user.UserEntity;
import com.sanus.sanus.domain.account.complete.presenter.CompleteRegisterPresenter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;


public class CompleteRegisterInteractorImpl implements CompleteRegisterInteractor {

    private final String TAG = this.getClass().getSimpleName();
    private CompleteRegisterPresenter presenter;
    private final int PICK_IMAGE_REQUEST = 234;

    private Uri imageUri;
    private UserEntity userEntity = new UserEntity();
    private ProgressDialog loading;
    private String idUser;
    private String sex = "Masculino";



    public CompleteRegisterInteractorImpl(CompleteRegisterPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, Context context) {
        switch (requestCode) {
            case PICK_IMAGE_REQUEST:

                if (resultCode == Activity.RESULT_OK && data != null) {

                    if (data.getData() == null) {
                        return;
                    }
                    imageUri = data.getData();
                    showImage();
                }
                break;
        }
    }

    private void showImage() {

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(presenter.getContentResolve(), imageUri);
            presenter.showImage(bitmap);
        } catch (Exception e) {
            Log.e(TAG, "Error mostrar imagen", e);
        }

    }

    @Override
    public void onClickGenderMale() {
        presenter.selectMale();
        sexo(1);
    }

    @Override
    public void onClickGenderFemale() {
        presenter.selectFemale();
        sexo(2);
    }

    @Override
    public void onClickImgCamera() {
        presenter.showFileChooser(PICK_IMAGE_REQUEST);
    }

    private void showLoading() {

        loading = presenter.getLoading();
        loading.setCancelable(false);
        loading.show();
        loading.setContentView(R.layout.alert_loading);
    }

    @Override
    public void onClickSaveData() {

        showLoading();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            idUser = auth.getCurrentUser().getUid();

            userEntity.nombre = presenter.getName();
            userEntity.apellido = presenter.getLastName();
            userEntity.edad = presenter.getEdadPosition();
            userEntity.tipo = "Paciente";
            userEntity.sexo = sex;

           if (imageUri != null) {
                try {
                    InputStream inputStream = presenter.getContentResolve().openInputStream(imageUri);
                    if (inputStream != null) {

                        userEntity.avatar = UUID.randomUUID().toString();

                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("avatar");
                        StorageReference file = storageReference.child(userEntity.avatar);

                        UploadTask uploadTask = file.putStream(inputStream);
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                                userEntity.avatar = "";
                                Log.e(TAG, "onFailure", exception);

                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                saveUser(userEntity);
                                Log.e(TAG, "sucesss");
                            }
                        });

                    }
                } catch (Exception e) {
                    presenter.showMessage(R.string.photo_error);
                    saveUser(userEntity);
                }

            } else {
                saveUser(userEntity);
            }

        }

    }

    private void saveUser(UserEntity userEntity) {

            FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
            mFirestore.collection("usuarios").document(idUser).set(userEntity).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    cancelLoading();
                    presenter.showMessage(R.string.signin_sucess);
                    presenter.goMain();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    presenter.showMessage(R.string.signin_error);
                }
            });
    }


    @Override
    public void validateButtonEnable() {

        if (!presenter.getName().isEmpty() && !presenter.getLastName().isEmpty()) {
            presenter.enableButton();
            return;
        }
        presenter.disableButton();
    }

    @Override
    public void setUpSpinner() {
        ArrayList<String> ageList = new ArrayList<>();
        for (int i = 0; i <= 98 ; i++) {

            if(i==0){
                ageList.add(i,String.format(Locale.getDefault(),"%d año",i+1));
            }else{
                ageList.add(i,String.format(Locale.getDefault(),"%d años",i+1));
            }

            presenter.setListSpinner(ageList);
        }
    }

    private void cancelLoading() {
        if (loading != null) {
            loading.dismiss();
        }
    }

    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
        cancelLoading();
        presenter.showMessage(R.string.upload_complete);
    }

    @Override
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
        loading.setMessage(((int) progress)+ "% subiendo");

        if (progress == 100){
            cancelLoading();
        }
    }

    @Override
    public void onFailure() {
        cancelLoading();
        presenter.showMessage(R.string.error);
    }

    private void sexo(int num){
        if(num==1){
            sex = "Masculino";
        }
        if(num==2){
            sex= "Femenino";
        }
    }
}
