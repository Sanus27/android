package com.sanus.sanus.domain.login.interactor;

import android.app.ProgressDialog;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sanus.sanus.R;
import com.sanus.sanus.domain.login.presenter.LoginPresenter;
import com.sanus.sanus.utils.regex.RegexUtils;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginPresenter presenter;
    private ProgressDialog loading;

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClickLogin() {

        if(presenter.getEmail().isEmpty() && !presenter.getPassword().isEmpty()){
            presenter.showMessage(R.string.empty_email);
            return;
        }

        if(!presenter.getEmail().isEmpty() && presenter.getPassword().isEmpty()){
            presenter.showMessage(R.string.empty_password);
            return;
        }

        if (presenter.getEmail().isEmpty() && presenter.getPassword().isEmpty()){
            presenter.showMessage(R.string.empty_email_password);
            return;
        }

        if (!presenter.getEmail().matches(RegexUtils.emailPattern())) {
            presenter.showMessage(R.string.error_invalid_email);
            return;
        }

        if(presenter.getPassword().length() < 6){
            presenter.showMessage(R.string.length_password);
            return;
        }

        showLoading();

        presenter.signInWithEmailAndPassword();

    }

    @Override
    public void signInWithEmailAndPasswordComplete(Task<AuthResult> task) {

        cancelLoading();

        if (!task.isSuccessful()) {
            presenter.showMessage(R.string.autenticacion_fallida);
        } else {
            presenter.goMain();
        }
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