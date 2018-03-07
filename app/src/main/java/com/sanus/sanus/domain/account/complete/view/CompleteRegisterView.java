package com.sanus.sanus.domain.account.complete.view;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.graphics.Bitmap;

import java.util.ArrayList;

public interface CompleteRegisterView {

    void setImageAvatar(Bitmap bitmap);
    void selectMale();
    void selectFemale();
    void showFileChooser(int requestCode);
    void setListSpinner(ArrayList<String> ageList);

    String getName();
    String getLastName();
    String getEdadPosition();

    void enableButton();
    void disableButton();

    void showMessage(int msg);
    void goMain();

    ContentResolver getContentResolve();
    void showImage(Bitmap bitmap);

    ProgressDialog getLoading();
}
