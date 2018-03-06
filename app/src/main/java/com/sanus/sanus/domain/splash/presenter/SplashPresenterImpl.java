package com.sanus.sanus.domain.splash.presenter;


import com.sanus.sanus.domain.splash.interactor.SplashInteractor;
import com.sanus.sanus.domain.splash.interactor.SplashInteractorImpl;
import com.sanus.sanus.domain.splash.view.SplashView;

public class SplashPresenterImpl implements SplashPresenter {
    private SplashView view;
    private SplashInteractor interactor;

    public SplashPresenterImpl(SplashView view) {
        this.view = view;
        interactor = new SplashInteractorImpl(this);
    }

    @Override
    public void init() {
        interactor.init();
    }

    @Override
    public void goLogin() {
        view.goLogin();
    }

    @Override
    public void goMain() {
        view.goMain();
    }

    @Override
    public void goCompleteRegister() {
        view.goCompleteRegister();
    }

    @Override
    public void showAlertRegister() {
        view.showAlertRegister();
    }

    @Override
    public void closeApp() {
        view.closeApp();
    }

    @Override
    public void acceptAlert() {
        interactor.acceptAlert();
    }

    @Override
    public void cancelAlert() {
        interactor.cancelAlert();
    }
}
