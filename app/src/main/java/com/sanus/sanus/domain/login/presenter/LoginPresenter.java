package com.sanus.sanus.domain.login.presenter;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sanus.sanus.domain.login.view.LoginView;

public interface LoginPresenter extends LoginView{

    void onClickLogin();
    void signInWithEmailAndPasswordComplete(Task<AuthResult> task);

    void validateButtonEnable();
}
