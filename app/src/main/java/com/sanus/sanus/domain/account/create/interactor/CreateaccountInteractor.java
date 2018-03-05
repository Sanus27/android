package com.sanus.sanus.domain.account.create.interactor;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface CreateaccountInteractor {

    void onClickSignIn();
    void createUserWithEmailAndPasswordComplete(Task<AuthResult> task);

    void validateButtonEnable();
}
