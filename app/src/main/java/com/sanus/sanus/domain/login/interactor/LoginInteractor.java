package com.sanus.sanus.domain.login.interactor;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface LoginInteractor {

    void onClickLogin();
    void signInWithEmailAndPasswordComplete(Task<AuthResult> task);

    void validateButtonEnable();
}
