package com.sanus.sanus.domain.account.complete.interactor;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.storage.UploadTask;

public interface CompleteRegisterInteractor {

    void onActivityResult(int requestCode, int resultCode, Intent data, Context context);
    void onClickGenderMale();
    void onClickGenderFemale();
    void onClickImgCamera();
    void onClickSaveData();

    void validateButtonEnable();
    void setUpSpinner();

    void onSuccess(UploadTask.TaskSnapshot taskSnapshot);
    void onProgress(UploadTask.TaskSnapshot taskSnapshot);
    void onFailure();
}
