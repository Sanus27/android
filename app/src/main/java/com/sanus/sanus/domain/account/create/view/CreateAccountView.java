package com.sanus.sanus.domain.account.create.view;

import android.app.ProgressDialog;

public interface CreateAccountView {

    String getEmail();
    String getPassword();

    void showMessage(int msg);
    void goCompleteAccount();
    void createUserWithEmailAndPassword();

    void enableButton();
    void disableButton();

    ProgressDialog getLoading();


}
