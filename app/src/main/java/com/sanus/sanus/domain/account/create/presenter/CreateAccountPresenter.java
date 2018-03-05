package com.sanus.sanus.domain.account.create.presenter;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.sanus.sanus.domain.account.create.view.CreateAccountView;

public interface CreateAccountPresenter extends CreateAccountView {

    void onClickSignIn();
    void createUserWithEmailAndPasswordComplete(Task<AuthResult> task);

    void validateButtonEnable();
}
