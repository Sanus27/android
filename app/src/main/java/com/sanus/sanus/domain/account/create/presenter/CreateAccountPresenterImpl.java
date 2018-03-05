package com.sanus.sanus.domain.account.create.presenter;

import android.app.ProgressDialog;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sanus.sanus.domain.account.create.interactor.CreateAccountInteractorImpl;
import com.sanus.sanus.domain.account.create.interactor.CreateaccountInteractor;
import com.sanus.sanus.domain.account.create.view.CreateAccountView;

public class CreateAccountPresenterImpl implements CreateAccountPresenter {
    private CreateAccountView view;
    private CreateaccountInteractor interactor;

    public CreateAccountPresenterImpl(CreateAccountView view) {
        this.view = view;
        interactor= new CreateAccountInteractorImpl(this);
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
    public void goCompleteAccount() {
        view.goCompleteAccount();
    }

    @Override
    public void createUserWithEmailAndPassword() {
        view.createUserWithEmailAndPassword();
    }

    @Override
    public ProgressDialog getLoading() {
        return view.getLoading();
    }

    @Override
    public void onClickSignIn() {
        interactor.onClickSignIn();
    }

    @Override
    public void createUserWithEmailAndPasswordComplete(Task<AuthResult> task) {
        interactor.createUserWithEmailAndPasswordComplete(task);
    }

    @Override
    public void validateButtonEnable() {
        interactor.validateButtonEnable();
    }

    @Override
    public void enableButton() {
        view.enableButton();
    }

    @Override
    public void disableButton() {
        view.disableButton();
    }
}
