package com.sanus.sanus.domain.account.complete.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sanus.sanus.domain.account.complete.interactor.CompleteRegisterInteractor;
import com.sanus.sanus.domain.account.complete.interactor.CompleteRegisterInteractorImpl;
import com.sanus.sanus.domain.account.complete.view.CompleteRegisterView;

public class CompleteRegisterPresenterImpl implements CompleteRegisterPresenter {
    private CompleteRegisterInteractor interactor;
    private CompleteRegisterView view;

    public CompleteRegisterPresenterImpl(CompleteRegisterView view) {
        this.view = view;
        interactor = new CompleteRegisterInteractorImpl(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, Context context) {
        interactor.onActivityResult(requestCode, resultCode, data, context);
    }

    @Override
    public void onClickGenderMale() {
        interactor.onClickGenderMale();
    }

    @Override
    public void onClickGenderFemale() {
        interactor.onClickGenderFemale();
    }

    @Override
    public void onClickImgCamera() {
        interactor.onClickImgCamera();
    }

    @Override
    public void onClickSaveData() {
        interactor.onClickSaveData();
    }

    @Override
    public void uploadFile() {
        interactor.uploadFile();
    }

    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        interactor.onSuccess(taskSnapshot);
    }

    @Override
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        interactor.onProgress(taskSnapshot);
    }

    @Override
    public void onFailure() {
        interactor.onFailure();
    }

    @Override
    public void setImageAvatar(Bitmap bitmap) {
        view.setImageAvatar(bitmap);
    }

    @Override
    public void selectMale() {
        view.selectMale();
    }

    @Override
    public void selectFemale() {
        view.selectMale();
    }

    @Override
    public void showFileChooser(int requestCode) {
        view.showFileChooser(requestCode);
    }

    @Override
    public void fileStorageReference(StorageReference fileRef, Uri filePath) {
        view.fileStorageReference(fileRef, filePath);
    }

    @Override
    public String getName() {
        return view.getName();
    }

    @Override
    public String getLastName() {
        return view.getLastName();
    }

    @Override
    public String getEdadPosition() {
        return view.getEdadPosition();
    }

    @Override
    public void showMessage(int msg) {
        view.showMessage(msg);
    }

    @Override
    public void goMain() {
        view.goMain();
    }

    @Override
    public ProgressDialog getLoading() {
        return view.getLoading();
    }
}
