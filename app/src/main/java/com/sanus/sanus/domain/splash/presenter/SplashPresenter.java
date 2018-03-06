package com.sanus.sanus.domain.splash.presenter;


import com.sanus.sanus.domain.splash.view.SplashView;
import com.sanus.sanus.utils.alert.CallbackAlert;

public interface SplashPresenter extends SplashView, CallbackAlert {

    void init();
}
