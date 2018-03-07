package com.sanus.sanus.domain.login.interactor;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanus.sanus.R;
import com.sanus.sanus.domain.login.presenter.LoginPresenter;
import com.sanus.sanus.utils.regex.RegexUtils;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginPresenter presenter;
    private ProgressDialog loading;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TAG = this.getClass().getSimpleName();

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClickLogin() {

        showLoading();
        presenter.signInWithEmailAndPassword();

    }

    @Override
    public void signInWithEmailAndPasswordComplete(Task<AuthResult> task) {

        cancelLoading();

        if (!task.isSuccessful()) {
            presenter.showMessage(R.string.autenticacion_fallida);
            return;
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

            String userIdNow = user.getUid();
            DocumentReference usuarios = db.collection("usuarios").document(userIdNow);
            usuarios.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            presenter.goMain();
                            Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        } else {
                            presenter.showAlertRegister();
                            Log.d(TAG, "No such document");
                        }
                    } else {
                       presenter.showMessage(R.string.error);
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
            return;
        }

        presenter.showMessage(R.string.error);


    }

    @Override
    public void validateButtonEnable() {
        if (presenter.getEmail().matches(RegexUtils.emailPattern()) && presenter.getPassword().length() > 6) {
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

    @Override
    public void acceptAlert() {
        presenter.goCompleteRegister();
    }

    @Override
    public void cancelAlert() {
        FirebaseAuth.getInstance().signOut();
    }
}
