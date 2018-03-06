package com.sanus.sanus.domain.account.create.interactor;


import android.app.ProgressDialog;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sanus.sanus.R;
import com.sanus.sanus.domain.account.create.presenter.CreateAccountPresenter;
import com.sanus.sanus.utils.regex.RegexUtils;

public class CreateAccountInteractorImpl implements CreateaccountInteractor {
    private CreateAccountPresenter presenter;
    private ProgressDialog loading;

    public CreateAccountInteractorImpl(CreateAccountPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClickSignIn() {

        showLoading();

        presenter.createUserWithEmailAndPassword();
    }

    @Override
    public void createUserWithEmailAndPasswordComplete(Task<AuthResult> task) {
        cancelLoading();

        if (!task.isSuccessful()) {
            presenter.showMessage(R.string.authenticate_failed);
        } else {
            presenter.goCompleteAccount();
        }
    }

    @Override
    public void validateButtonEnable() {
        if(presenter.getEmail().matches(RegexUtils.emailPattern()) && presenter.getPassword().length() > 6){
            presenter.enableButton();
            return;
        }
        presenter.disableButton();
    }

    private void showLoading() {

        loading = presenter.getLoading();
        loading.setCancelable(false);
        loading.show();
        loading.setContentView(R.layout.alert_loading);
    }

    private void cancelLoading() {
        if (loading != null) {
            loading.dismiss();
        }
    }
}
