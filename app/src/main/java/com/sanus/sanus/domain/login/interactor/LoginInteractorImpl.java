package com.sanus.sanus.domain.login.interactor;

import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanus.sanus.R;
import com.sanus.sanus.domain.login.presenter.LoginPresenter;
import com.sanus.sanus.domain.splash.interactor.UserEntity;
import com.sanus.sanus.utils.regex.RegexUtils;

public class LoginInteractorImpl implements LoginInteractor {
    private LoginPresenter presenter;
    private ProgressDialog loading;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String userIdNow;

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

            userIdNow = user.getUid();
            DocumentReference usuarios = db.collection("usuarios").document(userIdNow);
            if (usuarios.get().isSuccessful()) {
                usuarios.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        UserEntity complete = documentSnapshot.toObject(UserEntity.class);

                        if (complete.completo == null) {
                            presenter.showAlertRegister();
                            return;
                        }
                        if (complete.completo.equals("0") || complete.completo.isEmpty()) {
                            presenter.showAlertRegister();
                            return;
                        }
                        presenter.goMain();
                    }
                });

                return;
            }
            presenter.showAlertRegister();
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
