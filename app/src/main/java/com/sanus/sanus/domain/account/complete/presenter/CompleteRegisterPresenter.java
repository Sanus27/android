package com.sanus.sanus.domain.account.complete.presenter;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.storage.UploadTask;
import com.sanus.sanus.domain.account.complete.view.CompleteRegisterView;

public interface CompleteRegisterPresenter extends CompleteRegisterView {

    void onActivityResult(int requestCode, int resultCode, Intent data, Context context);
    void onClickGenderMale();
    void onClickGenderFemale();
    void onClickImgCamera();
    void onClickSaveData();
    void uploadFile();


    void onSuccess(UploadTask.TaskSnapshot taskSnapshot);
    void onProgress(UploadTask.TaskSnapshot taskSnapshot);
    void onFailure();

}
