package com.sanus.sanus.domain.login.presenter;

import android.app.ProgressDialog;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sanus.sanus.domain.login.interactor.LoginInteractor;
import com.sanus.sanus.domain.login.interactor.LoginInteractorImpl;
import com.sanus.sanus.domain.login.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView view;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView view) {
        this.view = view;
        interactor= new LoginInteractorImpl(this);
    }

    @Override
    public String getEmail() {
        return view.getEmail();
    }

    @Override
    public String getPassword() {
        return view.getPassword();
    }

    @Override
    public void showMessage(int msg) {
        view.showMessage(msg);
    }

    @Override
    public void signInWithEmailAndPassword() {
        view.signInWithEmailAndPassword();
    }

    @Override
    public void goMain() {
        view.goMain();
    }

    @Override
    public ProgressDialog getLoading() {
        return view.getLoading();
    }

    @Override
    public void onClickLogin() {
        interactor.onClickLogin();
    }

    @Override
    public void signInWithEmailAndPasswordComplete(Task<AuthResult> task) {
        interactor.signInWithEmailAndPasswordComplete(task);
    }
}