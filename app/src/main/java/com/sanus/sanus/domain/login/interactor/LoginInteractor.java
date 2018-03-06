package com.sanus.sanus.domain.login.interactor;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sanus.sanus.utils.alert.CallbackAlert;

public interface LoginInteractor extends CallbackAlert{

    void onClickLogin();
    void signInWithEmailAndPasswordComplete(Task<AuthResult> task);

    void validateButtonEnable();
}
