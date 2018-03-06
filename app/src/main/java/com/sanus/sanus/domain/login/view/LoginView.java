package com.sanus.sanus.domain.login.view;

import android.app.ProgressDialog;

public interface LoginView {

    String getEmail();
    String getPassword();

    void showMessage(int msg);
    void showAlertRegister();
    void signInWithEmailAndPassword();
    void enableButton();
    void disableButton();

    void goMain();
    void goCompleteRegister();

    ProgressDialog getLoading();

}
