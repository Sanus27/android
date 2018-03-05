package com.sanus.sanus.domain.account.create.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sanus.sanus.R;
import com.sanus.sanus.domain.account.complete.view.CompleteRegisterActivity;
import com.sanus.sanus.domain.account.create.presenter.CreateAccountPresenter;
import com.sanus.sanus.domain.account.create.presenter.CreateAccountPresenterImpl;
import com.sanus.sanus.domain.login.view.LoginActivity;
import com.sanus.sanus.utils.alert.AlertUtils;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountView {

    private CreateAccountPresenter presenter;
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        setUpVariable();
        setUpView();

    }

    private void setUpVariable() {

        if (presenter==null){
            presenter= new CreateAccountPresenterImpl(this);
        }
        auth = FirebaseAuth.getInstance();
    }

    private void setUpView() {

        inputEmail = findViewById(R.id.edCorreoE);
        inputPassword = findViewById(R.id.edContrasenia);
        Button btnSignUp = findViewById(R.id.btnCrearCuenta);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickSignIn();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public String getEmail() {
        return inputEmail.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return inputPassword.getText().toString().trim();
    }

    @Override
    public void showMessage(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void goCompleteAccount() {
        startActivity(new Intent(this, CompleteRegisterActivity.class));
        finish();
    }

    @Override
    public void createUserWithEmailAndPassword() {
        auth.createUserWithEmailAndPassword(getEmail(), getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        presenter.createUserWithEmailAndPasswordComplete(task);
                    }
                });
    }

    @Override
    public ProgressDialog getLoading() {
        return AlertUtils.getLoading(this);
    }





}
