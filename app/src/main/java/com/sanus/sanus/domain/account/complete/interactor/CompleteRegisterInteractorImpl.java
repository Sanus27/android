package com.sanus.sanus.domain.account.complete.interactor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanus.sanus.R;
import com.sanus.sanus.domain.account.complete.presenter.CompleteRegisterPresenter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CompleteRegisterInteractorImpl implements CompleteRegisterInteractor {
    private CompleteRegisterPresenter presenter;
    private final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    private String sex = "Masculino";
    private ProgressDialog loading;


    public CompleteRegisterInteractorImpl(CompleteRegisterPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, Context context) {
        if(requestCode == PICK_IMAGE_REQUEST && data != null){
            filePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), filePath);
                presenter.setImageAvatar(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @Override
    public void onClickSaveData() {
        FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){

            String id = auth.getCurrentUser().getUid();
            String name = presenter.getName();
            String apelli = presenter.getLastName();
            String edaD = presenter.getEdadPosition();

            Map<String, String> userMap = new HashMap<>();
            userMap.put("tipo", "Paciente");
            userMap.put("nombre", name);
            userMap.put("apellido", apelli);
            userMap.put("edad", edaD);
            userMap.put("sexo", sex);
            userMap.put("completo", "finalizado");
            userMap.put("avatar", "avatar.png");

            mFirestore.collection("usuarios").document(id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
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



    }

    @Override
    public void uploadFile() {
        if(filePath != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();

            loading = presenter.getLoading();
            loading.setCancelable(false);
            loading.setTitle(R.string.uploading);
            loading.show();

            StorageReference fileRef = storageReference.child("images/profile.jpg");

            presenter.fileStorageReference(fileRef, filePath);

        }else{
            presenter.showMessage(R.string.error);
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
