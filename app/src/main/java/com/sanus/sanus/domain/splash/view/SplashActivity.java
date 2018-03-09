package com.sanus.sanus.domain.splash.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sanus.sanus.R;
import com.sanus.sanus.domain.account.complete.view.CompleteRegisterActivity;
import com.sanus.sanus.domain.login.view.LoginActivity;
import com.sanus.sanus.domain.main.view.MainActivity;
import com.sanus.sanus.domain.splash.presenter.SplashPresenter;
import com.sanus.sanus.domain.splash.presenter.SplashPresenterImpl;
import com.sanus.sanus.utils.alert.AlertUtils;
import com.sanus.sanus.utils.alert.CallbackAlert;

public class SplashActivity extends AppCompatActivity implements SplashView, CallbackAlert {

    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setUpVariable();

        presenter.init();
    }

    private void setUpVariable() {
        if (presenter == null) {
            presenter = new SplashPresenterImpl(this);
        }
    }

    @Override
    public void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void goCompleteRegister() {
        Intent intent = new Intent(this, CompleteRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public void showAlertRegister() {
        AlertUtils alertUtils = new AlertUtils(this);
        alertUtils.registerAlert(this, getString(R.string.complete_register));
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void acceptAlert() {
        presenter.acceptAlert();
    }

    @Override
    public void cancelAlert() {
        presenter.cancelAlert();
    }
}
